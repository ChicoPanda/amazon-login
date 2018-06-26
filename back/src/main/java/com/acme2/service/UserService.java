package com.acme2.service;

import java.sql.SQLException;
import java.util.List;

import com.acme2.model.User;
import com.acme2.model.UserRequest;

public interface UserService {
 
  User findById(Long id);

  User findByUsername(String username);

  List<User> findAll();

  User save(UserRequest user);

  User remove(User userRequest) throws SQLException;

  UserRequest update(UserRequest userRequest, User user);
  
}
