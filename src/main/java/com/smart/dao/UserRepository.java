package com.smart.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);
	
	User findByEmail(String email);
    boolean existsByEmail(String email);
    
    User findByName(String username);
    List<User> findByRole(String role);

	
	
}
