package com.swalayan.service;

import com.swalayan.models.UserDTO;
import com.swalayan.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PenggunaService {

    @Autowired
    private PenggunaRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return repo.findAllUsers();
    }

    public void tambahUser(UserDTO user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        repo.save(user);
    }

    public UserDTO getUser(Integer id, String role) {
        return repo.findById(id, role);
    }

    public void updateUser(UserDTO user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
        }
        repo.update(user);
    }

    public void hapusUser(Integer id, String role) {
        repo.delete(id, role);
    }
}