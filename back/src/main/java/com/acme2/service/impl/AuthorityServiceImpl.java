package com.acme2.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.acme2.model.Authority;
import com.acme2.repository.AuthorityRepository;
import com.acme2.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	  @Autowired
	  private AuthorityRepository authorityRepository;

	  @Override
	  public Authority findById(Long id) {
	    // Auto-generated method stub
	    return this.authorityRepository.findOne(id);
	  }

	  @Override
	  public Authority findByname(String name) {
	    // Auto-generated method stub
	    return this.authorityRepository.findByName(name);
	  }

	 
	  @PreAuthorize("hasRole('ADMIN')")
	  public List<Authority> findAll() {
	    return authorityRepository.findAll();
	  }
	  
	  @PreAuthorize("hasRole('ADMIN')")
	  @Override
		public void update(final Authority authority) {
			this.authorityRepository.save(authority);
		}

	  @Override
		public Authority save(final Authority authority) {
			this.authorityRepository.save(authority);
			return authority;
		}
	  
	@PreAuthorize("hasRole('ADMIN')")
	  @Override
	  public void remove(Authority authorityRequest) throws SQLException { 
		    this.authorityRepository.delete(authorityRequest);
	  }


}
