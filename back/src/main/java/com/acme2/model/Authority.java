package com.acme2.model;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name="Authority")
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -4760454710486708886L;

	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="name")
    String name;
    

	
    @Override
    public String getAuthority() {
        return name;
    }
}
