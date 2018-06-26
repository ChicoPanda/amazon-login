package com.acme2.service.impl;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acme2.model.User;
import com.acme2.repository.UserRepository;
import com.amazonaws.auth.AWSCredentials;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	AWSCredentials credentials;

	protected final Log looger = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			User user;
			if (username.contains("data:image/png;base64,")) {
				System.err.println("ss");
				AwsServiceImpl awsServiceImpl = new AwsServiceImpl();
				String faceId = awsServiceImpl.compareFace(username.split(",")[1].replaceAll(" ", "+"));
				faceId = faceId.replaceAll("\"", "");
				user = userRepository.findByUsername(faceId);
			} else {
				user = userRepository.findByUsername(username);
			} 
			if (user == null) {	
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			} else {
				return user;
			}
			
		} catch (Exception e) {
			System.err.println("ERROR AL OBTENER EL USUARIO EN CUSTOMDETAILS "+e);
			return null;
		}
		
	}
	public void changePassword(String oldPassword, String newPassword) {
		
		try {
			Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
			String username = currentUser.getName();

			if (authenticationManager != null) {
				looger.debug("Re-authenticating user '" + username + "' for password change request.");

				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
			} else {
				looger.debug("No authentication manager set. can't change Password!");

				return;
			}

			looger.debug("Changing password for user '" + username + "'");

			User user = (User) loadUserByUsername(username);

			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);

		} catch (Exception e) {
			System.err.println("ERROR AL CAMBIAR LA CONTRASEÑA "+e);
		}
		
	}

}
