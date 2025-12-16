package com.swalayan.service;

import com.swalayan.models.UserDTO;
import com.swalayan.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PenggunaRepository repo;
    
    @Autowired
    private PasswordEncoder passwordEncoder; 
    public UserDTO authenticate(String username, String rawPassword) {

        UserDTO user = repo.findByUsername(username);


        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            

            user.setPassword(null); 
            return user;
        }
        
        return null; 
    }
}