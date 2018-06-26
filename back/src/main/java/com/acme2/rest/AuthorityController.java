package com.acme2.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.acme2.exception.ResourceConflictException;
import com.acme2.model.Authority;
import com.acme2.service.AuthorityService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(method = GET, value = "/authority/{authorityId}")
	public Authority loadById(@PathVariable Long authorityId) {
		return this.authorityService.findById(authorityId);
	}

	@RequestMapping(method = PUT, value = "/authority/{authorityId}")
	public void put(@RequestBody Authority authority) {
		this.authorityService.update(authority);
	}

	@RequestMapping(method = GET, value = "/authority/all")
	public List<Authority> loadAll() {
		return this.authorityService.findAll();
	}

	@RequestMapping(method = DELETE, value = "/authority/{authorityId}")
	public void delete(@PathVariable Long authorityId) throws SQLException {
		this.authorityService.remove(loadById(authorityId));
	}

	@RequestMapping(method = POST, value = "/addAuthority")
	public ResponseEntity<Object> addAuthority(@RequestBody Authority authorityRequest,
			UriComponentsBuilder ucBuilder) {

		Authority existAuthority = this.authorityService.findByname(authorityRequest.getName());
		if (existAuthority != null) {
			throw new ResourceConflictException(authorityRequest.getId(), "Authorityname already exists");
		}
		Authority authority = this.authorityService.save(authorityRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/authority/{authorityId}").buildAndExpand(authority.getId()).toUri());
		return new ResponseEntity<>(authority, HttpStatus.CREATED);
	}

}
