package com.swalayan.repository;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class PrediksiStockRepository {

    private final JdbcTemplate db;

    public PrediksiStockRepository(JdbcTemplate db) {
        this.db = db;
    }

    public int getStokBarang(Long barangId) {
        String sql = "SELECT stok FROM Barang WHERE id_barang = ?";
        try {
            return db.queryForObject(sql, Integer.class, barangId);
        } catch (Exception e) {
            return 0; 
        }
    }

    public int getTotalTerjual30Hari(Long barangId) {
        String sql = "SELECT COALESCE(SUM(dp.jumlah), 0) " +
                     "FROM DetailPenjualan dp " +
                     "JOIN TransaksiPenjualan tp ON dp.id_penjualan = tp.id_penjualan " +
                     "WHERE dp.id_barang = ? " +
                     "AND tp.tanggal_penjualan >= DATEADD(day, -30, GETDATE())";
        
        try {
            return db.queryForObject(sql, Integer.class, barangId);
        } catch (Exception e) {
            return 0;
        }
    }
    

}