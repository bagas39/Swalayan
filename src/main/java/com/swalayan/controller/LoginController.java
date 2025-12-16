package com.swalayan.controller;

import com.swalayan.models.UserDTO;
import com.swalayan.repository.PenggunaRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private PenggunaRepository penggunaRepo; 

    @GetMapping("/login")
    public String loginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String role = auth.getAuthorities().iterator().next().getAuthority();
            if (role.equals("Gudang")) return "redirect:/manajemen_stok";
            else if (role.equals("Owner")) return "redirect:/laporan_keuangan";
            else if (role.equals("Supervisor")) return "redirect:/transaksi_penjualan";
            else return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/api/me")
    @ResponseBody
    public ResponseEntity<?> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }

        UserDTO user = penggunaRepo.findByUsername(auth.getName());

        return ResponseEntity.ok(Map.of(
            "id", user.getId(), 
            "username", user.getUsername(),
            "nama", user.getNama(),
            "role", user.getRole()
        ));
    }
}