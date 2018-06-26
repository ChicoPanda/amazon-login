package com.acme2.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme2.exception.ResourceConflictException;
import com.acme2.model.User;
import com.acme2.model.UserRequest;
import com.acme2.service.UserService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	Map<String, String> result = new HashMap<>();

	String resultString = "result";
	String successString = "success";

	@Autowired
	private UserService userService;

	@RequestMapping(method = GET, value = "/user/{userId}")
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}

	@RequestMapping(method = PUT, value = "/user/{userId}")
	public ResponseEntity<Map> put(@RequestBody UserRequest user) {

		User user1;
		user1 = this.userService.findById(user.getId());
		this.userService.update(user, user1);
		result.put(resultString, successString);
		return ResponseEntity.accepted().body(result);
	}

	@RequestMapping(method = GET, value = "/user/all")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@RequestMapping(method = DELETE, value = "/user/{userId}")
	public ResponseEntity<Map> delete(@PathVariable Long userId) throws SQLException {
		this.userService.remove(loadById(userId));
		result.put(resultString, successString);
		return ResponseEntity.accepted().body(result);
	}

 

	@RequestMapping(method = POST, value = "/signup")
	public ResponseEntity<User> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

		User existUser = this.userService.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getId(), "Username already exists");
		}
		User user = this.userService.save(userRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	/*
	 * We are not using userService.findByUsername here(we could), so it is good
	 * that we are making sure that the user has role "ROLE_USER" to access this
	 * endpoint.
	 */
	@RequestMapping("/whoami") 
	public User user() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
