package com.swalayan.service;

import com.swalayan.models.LaporanKeuangan;
import com.swalayan.repository.LaporanRepository;
import org.springframework.stereotype.Service;

@Service
public class LaporanService {

    private final LaporanRepository repo;

    public LaporanService(LaporanRepository repo) {
        this.repo = repo;
    }

    public LaporanKeuangan getLaporanBulanan(String periode) {
        double pendapatan = repo.getTotalPendapatan(periode);
        double pengeluaran = repo.getTotalPengeluaran(periode);

        return new LaporanKeuangan(pendapatan, pengeluaran, periode);
    }
}
