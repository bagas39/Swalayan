package com.swalayan.controller;

import com.swalayan.models.Barang;
import com.swalayan.service.BarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class BarangController {

    @Autowired
    private BarangService barangService;

    @GetMapping("/api/barang")
    public List<Barang> getBarang() {
        return barangService.getAllForKasir();
    }

    @GetMapping("/api/manajemen_stok")
    public Map<String, Object> listStok(@RequestParam(defaultValue="0") int offset,
                                        @RequestParam(defaultValue="15") int limit,
                                        @RequestParam(required=false) String search_nama) {
        return barangService.getListStokPaged(offset, limit, search_nama);
    }
}