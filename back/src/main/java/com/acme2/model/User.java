package com.acme2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 2843323173830983018L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "imageprofile")
	private String imageprofile;
	
	@ManyToOne
	@JoinColumn(name = "rol")
	private Authority rol;

	// We can add the below fields in the users table.
	// For now, they are hardcoded.
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Authority> autorities = new ArrayList<>();
		autorities.add(rol);
		return autorities;
	}
}
