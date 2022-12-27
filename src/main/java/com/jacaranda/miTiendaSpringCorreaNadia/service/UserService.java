package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jacaranda.miTiendaSpringCorreaNadia.model.UserException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository usersRepository;

	public Users getUser(String username) {
		return usersRepository.findById(username).orElse(null);
	}

	public List<Users> getUsers() {
		return usersRepository.findAll();
	}

	public Users addUser(Users newUser) throws UserException {
		
		// checking if user exists
		Users user = getUser(newUser.getUsername());
		
		// if it doesn't exist the it needs to be added
		if (user == null) {
			
			// before adding user the password needs to be encrypted
			String encryptedPass = DigestUtils.md5DigestAsHex(newUser.getPassword().getBytes());

			try {
				// setting the encrypted password
				newUser.setPassword(encryptedPass);
				// setting admin as false - as when adding a user it has to be a normal user
				newUser.setAdmin(false);
				// saving user in DB
				user = usersRepository.save(newUser);
				
			} catch (Exception  e) {
				e.printStackTrace();
				throw new UserException("Ha habido un error al insertar el usuario en la base de datos.");
			}
			
		} else {
			throw new UserException("El usuario ya existe en la base de datos.");
		}
		
		return user;
	}

	public void deleteUser(Users user) throws UserException {
		
		if(getUser(user.getUsername()) != null) {
			usersRepository.delete(user);
			
		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}
		
		
	}

	public Users updateUser(Users user) throws UserException {
		
		Users existingUser = getUser(user.getUsername());
		
		if ( existingUser!= null) {
			
			user.setPassword(existingUser.getPassword());
			user.setAdmin(existingUser.isAdmin());
			
			return usersRepository.save(user);
			
		} else {
			throw new UserException("El usuario no existe en la base de datos.");
		}
	}

}
