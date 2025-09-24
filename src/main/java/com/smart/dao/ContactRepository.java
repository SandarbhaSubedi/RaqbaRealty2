package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
}

