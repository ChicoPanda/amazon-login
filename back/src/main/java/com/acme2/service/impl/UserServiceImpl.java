package com.acme2.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acme2.model.Authority;
import com.acme2.model.User;
import com.acme2.model.UserRequest;
import com.acme2.repository.UserRepository;
import com.acme2.service.AuthorityService;
import com.acme2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Override
	// @PreAuthorize("hasRole('USER')")
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	public User findById(Long id) {
		return userRepository.findOne(id);
	}


	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public UserRequest update(UserRequest userRequest, User user) {
		try {
			user.setUsername(userRequest.getUsername());
			user.setFirstname(userRequest.getFirstname());
			user.setRol(userRequest.getRol());

			this.userRepository.save(user);
			return userRequest;
		} catch (Exception e) {
			System.err.println("ERROR AL ACTUALIZAR(EDITAR) UN USUARIO " + e);
			return null;
		}

	}

	@Override
	public User save(final UserRequest userRequest) {
		try {
			AwsServiceImpl awsServiceImpl = new AwsServiceImpl();
			String faceId = awsServiceImpl.addFaceImage(userRequest.getUsername(), userRequest.getFirstname());
			final User user = new User();
			user.setRol(userRequest.getRol());
			user.setId(userRequest.getId());
			user.setUsername(faceId);
			user.setFirstname(userRequest.getFirstname());
			user.setImageprofile(userRequest.getImageprofile());
			user.setPassword(userRequest.getPassword());
			if (userRequest.getRol() == null) {
				Authority auth = authService.findByname("ROLE_USER");
				user.setRol(auth);
			}
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			this.userRepository.save(user);
			return user;
		} catch (Exception e) {
			System.err.println("ERROR AL CREAR UN USUARIO " + e);
			return null;
		}

	}

	@Override
	public User remove(User userRequest) throws SQLException {
		try {
			User user = new User();

			user.setId(userRequest.getId());
			user.setUsername(userRequest.getUsername());
			user.setFirstname(userRequest.getFirstname());
			user.setRol(userRequest.getRol());
			this.userRepository.delete(user);
			return user;
		} catch (Exception e) {
			System.err.println("ERROR AL ELIMINAR UN USUARIO "+e);
			return null;
		}
		
	}

}
