package com.swalayan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/") public String pageKasir() { return "kasir"; } 
    @GetMapping("/transaksi_penjualan") public String pageTransaksi() { return "transaksi_penjualan"; }
    @GetMapping("/manajemen_stok") public String pageStok() { return "manajemen_stok"; }
    @GetMapping("/stok_opname") public String pageOpname() { return "stok_opname"; }
    @GetMapping("/prediksi_stok") public String pagePrediksi() { return "prediksi_stok"; }
    @GetMapping("/transaksi_pembelian") public String pagePembelian() { return "transaksi_pembelian"; }
    @GetMapping("/laporan_keuangan") public String pageLaporan() { return "laporan_keuangan"; }
    @GetMapping("/manajemen_pengguna") public String pagePengguna() { return "manajemen_pengguna"; }    
}