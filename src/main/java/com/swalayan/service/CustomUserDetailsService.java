package com.swalayan.service;

import com.swalayan.models.UserDTO;
import com.swalayan.repository.PenggunaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PenggunaRepository repo;

    public CustomUserDetailsService(PenggunaRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO appUser = repo.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword()) 
                .authorities(appUser.getRole())  
                .build();
    }
}