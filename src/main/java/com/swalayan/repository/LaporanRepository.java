package com.swalayan.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class LaporanRepository {

    private final JdbcTemplate db;

    public LaporanRepository(JdbcTemplate db) {
        this.db = db;
    }

    public double getTotalPendapatan(String periode) {

        try {
            String[] parts = periode.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);


            System.out.println("DEBUG PENDAPATAN -> Tahun: " + year + ", Bulan: " + month);

            String sql = "SELECT COALESCE(SUM(total_harga), 0) FROM TransaksiPenjualan " +
                         "WHERE YEAR(tanggal_penjualan) = ? AND MONTH(tanggal_penjualan) = ?";
            
            Double result = db.queryForObject(sql, Double.class, year, month);
            System.out.println("DEBUG HASIL PENDAPATAN: " + result);
            return result != null ? result : 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getTotalPengeluaran(String periode) {
        try {
            String[] parts = periode.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            System.out.println("DEBUG PENGELUARAN -> Tahun: " + year + ", Bulan: " + month);

            String sql = "SELECT COALESCE(SUM(total_pembelian), 0) FROM TransaksiPembelian " +
                         "WHERE YEAR(tanggal_pembelian) = ? AND MONTH(tanggal_pembelian) = ?";

            Double result = db.queryForObject(sql, Double.class, year, month);
            System.out.println("DEBUG HASIL PENGELUARAN: " + result);
            return result != null ? result : 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}