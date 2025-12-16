package com.swalayan.service;

import com.swalayan.models.PrediksiStok;
import com.swalayan.repository.PrediksiStockRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PrediksiStockService {

    private final PrediksiStockRepository repo;

    public PrediksiStockService(PrediksiStockRepository repo) {
        this.repo = repo;
    }

    public PrediksiStok hitungEstimasiHari(Long barangId) {

        int stokSaatIni = repo.getStokBarang(barangId);
        int totalTerjual30Hari = repo.getTotalTerjual30Hari(barangId);
 
        double rataRataHarian = Math.ceil(totalTerjual30Hari / 30.0); 
        int avgSales = (int) rataRataHarian; 

        int hariBertahan;

        if (stokSaatIni <= 0) {
            hariBertahan = 0; 
        } else if (avgSales <= 0) {
            hariBertahan = 999; 
        } else {
            hariBertahan = stokSaatIni / avgSales; 
        }

        PrediksiStok p = new PrediksiStok();
        p.setBarangId(barangId);
        p.setStokSaatIni(stokSaatIni);
        p.setHariBertahan(hariBertahan); 
        
        int estimasiKebutuhan = avgSales * 30;
        p.setPrediksiKebutuhan(stokSaatIni - estimasiKebutuhan);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        p.setTanggalPrediksi(LocalDateTime.now().format(dtf));

        return p;
    }
}