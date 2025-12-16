package com.swalayan.controller;

import com.swalayan.models.PrediksiStok;
import com.swalayan.service.PrediksiStockService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/prediksi")
@CrossOrigin(origins = "*")
public class PrediksiController {

    private final PrediksiStockService service;

    public PrediksiController(PrediksiStockService service) {
        this.service = service;
    }

    @PostMapping("/stok")
    public PrediksiStok hitung(@RequestBody Map<String, Object> payload) {
        Long barangId = Long.valueOf(payload.get("barangId").toString());
        return service.hitungEstimasiHari(barangId);
    }
}