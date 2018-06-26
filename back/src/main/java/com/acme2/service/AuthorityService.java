package com.acme2.service;

import java.sql.SQLException;
import java.util.List;

import com.acme2.model.Authority;

public interface AuthorityService {

	  Authority findById(Long id);

	  Authority findByname(String name);
	  
	  List<Authority> findAll();

	  Authority save(Authority authority);

	  void remove(Authority authorityRequest) throws SQLException;

	  void update(Authority authority);

}
