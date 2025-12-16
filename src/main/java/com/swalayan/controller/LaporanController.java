package com.swalayan.controller;

import com.swalayan.models.LaporanKeuangan;
import com.swalayan.service.LaporanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/laporan")
@CrossOrigin(origins = "*") 
public class LaporanController {

    private final LaporanService laporanService;

    public LaporanController(LaporanService laporanService) {
        this.laporanService = laporanService;
    }


    @GetMapping("/keuangan")
    public LaporanKeuangan getLaporan(@RequestParam String periode) {
        

        LaporanKeuangan laporan = laporanService.getLaporanBulanan(periode);
        System.out.println("Cek Data: " + laporan.getTotalPendapatan()); 
        

        return laporan; 
    }
}