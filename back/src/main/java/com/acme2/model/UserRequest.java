package com.acme2.model;

import lombok.Data;

@Data
public class UserRequest {

	private Long id;

	private String username;

	private String password;

	private String firstname;

	private String lastname;

	private String imagelog;

	private String imageprofile;

	private Authority rol;

}
