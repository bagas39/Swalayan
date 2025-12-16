package com.swalayan.controller;

import com.swalayan.models.PembelianRequest;
import com.swalayan.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pembelian")
public class PembelianController {

    @Autowired
    private PembelianService pembelianService;

    @PostMapping
    public ResponseEntity<?> createPembelian(@RequestBody PembelianRequest request) {
        try {
            return ResponseEntity.ok(pembelianService.createPembelian(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Terjadi kesalahan server: " + e.getMessage()));
        }
    }

    @GetMapping("/history")
    public Map<String, Object> getHistoryPembelian(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "15") int limit,
            @RequestParam(required = false) String search_supplier,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date) {
        return pembelianService.getHistoryPembelian(offset, limit, search_supplier, start_date, end_date);
    }
}