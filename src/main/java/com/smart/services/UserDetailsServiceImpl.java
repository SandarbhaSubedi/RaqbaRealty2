package com.smart.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
        	user.getEmail(), // important: use email as username
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
    
}

