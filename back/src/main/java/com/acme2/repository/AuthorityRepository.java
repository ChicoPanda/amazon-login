package com.acme2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme2.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
