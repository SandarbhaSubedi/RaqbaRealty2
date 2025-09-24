package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(encoder.encode("Raqba@realtyweb@123"));
            admin.setRole("USER");
            admin.setName("user");
            userRepository.save(admin);
        }
    
    
   
}
}
