package com.swalayan.controller;

import com.swalayan.models.UserDTO;
import com.swalayan.service.PenggunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class PenggunaController {

    @Autowired
    private PenggunaService service;

    @GetMapping
    public List<UserDTO> listUsers() {
        return service.getAllUsers();
    }
    
    @PostMapping
    public Map<String, Object> createUser(@RequestBody UserDTO user) {
        try {
            service.tambahUser(user);
            return Map.of("success", true, "message", "User berhasil ditambahkan");
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    @GetMapping("/{role}/{id}")
    public ResponseEntity<?> getUser(@PathVariable String role, @PathVariable Integer id) {
        UserDTO user = service.getUser(id, role);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{role}/{id}")
    public Map<String, Object> updateUser(@PathVariable String role, @PathVariable Integer id, @RequestBody UserDTO user) {
        try {
            user.setId(id);
            user.setRole(role); 
            service.updateUser(user);
            return Map.of("success", true, "message", "User berhasil diperbarui");
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    @DeleteMapping("/{role}/{id}")
    public Map<String, Object> deleteUser(@PathVariable String role, @PathVariable Integer id) {
        try {
            service.hapusUser(id, role);
            return Map.of("success", true, "message", "User berhasil dihapus");
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
}