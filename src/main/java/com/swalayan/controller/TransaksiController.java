package com.swalayan.controller;

import com.swalayan.models.TransaksiRequest;
import com.swalayan.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @PostMapping("/api/transaksi")
    public Map<String, Object> create(@RequestBody TransaksiRequest request) {
        return transaksiService.create(request);
    }

    @PutMapping("/api/transaksi/{id}")
    public Map<String, Object> edit(@PathVariable int id, @RequestBody TransaksiRequest request) {
        return transaksiService.edit(id, request);
    }

    @GetMapping("/api/transaksi_penjualan")
    public Map<String, Object> list(@RequestParam(defaultValue="0") int offset, 
                                    @RequestParam(defaultValue="15") int limit,
                                    @RequestParam(required=false) String search_id,
                                    @RequestParam(required=false) String start_date,
                                    @RequestParam(required=false) String end_date) {
        return transaksiService.getHistoryPaged(offset, limit, search_id, start_date, end_date);
    }

    @GetMapping("/api/transaksi_detail/{id}")
    public Map<String, Object> detail(@PathVariable int id) {
        return transaksiService.getDetail(id);
    }
}