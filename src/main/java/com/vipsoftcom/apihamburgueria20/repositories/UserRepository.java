package com.vipsoftcom.apihamburgueria20.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.vipsoftcom.apihamburgueria20.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByEmail(String email);
}
