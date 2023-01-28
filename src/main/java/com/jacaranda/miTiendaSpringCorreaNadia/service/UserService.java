package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jacaranda.miTiendaSpringCorreaNadia.model.UserException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.UserPassword;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository usersRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	Cloudinary cloudinaryConfig;

	public Users getUser(String username) {
//		return usersRepository.findById(username).orElse(null);
		return usersRepository.findByUser(username);
	}

	public List<Users> getUsers() {
		return usersRepository.findAll();
	}

	public Users addUser(Users newUser, String url) throws UserException {

		// checking if user exists
		Users user = getUser(newUser.getUsername());

		// if it doesn't exist the it needs to be added
		if (user == null) {

			// before adding user the password needs to be encrypted
			BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
			String encryptedPass = passEncoder.encode(newUser.getPassword());

			try {
				// setting the encrypted password
				newUser.setPassword(encryptedPass);
				// setting role as user - as when adding a user it has to be a normal user
				newUser.setRole("USER");
				// creating verification code
				String randomCode = RandomString.make(64);
				newUser.setVerificationcode(randomCode);
				newUser.setEnabled(false);
				// saving user in DB
				user = usersRepository.save(newUser);
				sendVerificationEmail(user, url);

			} catch (Exception e) {
				e.printStackTrace();
				throw new UserException("Ha habido un error al crear el usuario.");
			}

		} else {
			throw new UserException("El usuario ya existe.");
		}

		return user;
	}

	private void sendVerificationEmail(Users user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "jacarandasnc@gmail.com";
		String senderName = "Jacaranda";
		String subject = "Please verify your registration";
		String content = "Dear [[user]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Your company name.";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[user]]", user.getUsername());
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationcode();
		content = content.replace("[[URL]]", verifyURL);
		helper.setText(content, true);
		mailSender.send(message);
	}

	public void deleteUser(Users user) throws UserException {

		if (getUser(user.getUsername()) != null) {
			usersRepository.delete(user);

		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}

	}

	public Users updateUser(Users user) throws UserException {

		Users existingUser = getUser(user.getUsername());

		if (existingUser != null) {

			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setImage(user.getImage());

			return usersRepository.save(existingUser);

		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}
	}

	public Users updateAdmin(Users user) throws UserException {

		Users existingUser = getUser(user.getUsername());

		if (existingUser != null) {
			existingUser.setRole(user.getRole());

			return usersRepository.save(existingUser);

		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}

	}

	public void verifyUser(String code) throws UserException {
		Users existingUser = usersRepository.findByVerificationcode(code);

		if (existingUser != null) {
			existingUser.setEnabled(true);
			existingUser.setVerificationcode("");

			usersRepository.save(existingUser);

		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users existingUser = usersRepository.findByUser(username);

		if (existingUser == null) {
			throw new UsernameNotFoundException("No existe el usuario.");
		}

		return existingUser;
	}
	
	
	public Users loggedUser() {
		Users result = new Users();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		result = (Users) auth.getPrincipal();
		
		return result;
	}
	

	public Page<Users> findAllUsers(int pageNum, int pageSize, String sortField, String stringFind) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortField).ascending());

		if (stringFind.equals("")) {
			return usersRepository.findAll(pageable);
		} else {
			return usersRepository.findByUsernameLike("%" + stringFind + "%", pageable);
		}

	}

	public Users updatePassword(UserPassword userPassword) throws UserException {

		Users existingUser = getUser(userPassword.getUsername());

		if (existingUser != null) {

			if (userPassword.getNewPassword().equals(userPassword.getConfirmPassword())) {

				BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
				if (passEncoder.matches(userPassword.getOldPassword(), existingUser.getPassword())) {
					String encodedPassword = passEncoder.encode(userPassword.getNewPassword());

					existingUser.setPassword(encodedPassword);

					return usersRepository.save(existingUser);

				} else {
					throw new UserException("Contraseña incorrecta.");
				}

			} else {
				throw new UserException("La nueva contraseña y su confirmación no coinciden.");
			}

		} else {
			throw new UserException("No existe el usuario.");
		}

	}

	public String uploadFile(MultipartFile file) {
		
		try {
			
			File uploadedFile = convertMultiPartToFile(file);
			
			Map<String, String> uploadResul = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
			boolean isDeleted = uploadedFile.delete();
			
			if(isDeleted) {
				System.out.println("File successfully deleted");
			}else {
				System.out.println("File doesn't exist");
			}
			return uploadResul.get("url").toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		
		return convFile; 
	}
}

