package com.swalayan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class PembelianRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createHeader(String supplier, Integer idGudang) {
        String sql = "INSERT INTO TransaksiPembelian (supplier_name, id_gudang, total_pembelian) " +
                     "OUTPUT INSERTED.id_pembelian " + 
                     "VALUES (?, ?, 0)";
        return jdbcTemplate.queryForObject(sql, Integer.class, supplier, idGudang);
    }

    public int createBarangBaru(String nama, BigDecimal beli, BigDecimal jual) {
        String sql = "INSERT INTO Barang (nama_barang, harga_beli, harga_jual, stok, kategori) " +
                     "OUTPUT INSERTED.id_barang " +
                     "VALUES (?, ?, ?, 0, 'Umum')";
        
        return jdbcTemplate.queryForObject(sql, Integer.class, nama, beli, jual);
    }

    public Integer findIdBarangByName(String namaBarang) {
        try {
            String sql = "SELECT TOP 1 id_barang FROM Barang WHERE nama_barang = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, namaBarang);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void saveDetailsBatch(List<Object[]> batchDetail) {
        String sql = "INSERT INTO DetailPembelian (id_pembelian, id_barang, jumlah_beli, harga_beli_satuan, subtotal_beli) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, batchDetail);
    }

    public void updateHeaderTotal(BigDecimal total, int idPembelian) {
        jdbcTemplate.update("UPDATE TransaksiPembelian SET total_pembelian = ? WHERE id_pembelian = ?", total, idPembelian);
    }

    public void updateStokBatch(List<Object[]> batchStok) {
        jdbcTemplate.batchUpdate("UPDATE Barang SET stok = stok + ? WHERE id_barang = ?", batchStok);
    }
    
    public Integer countData(String sql, Object[] params) {
        return jdbcTemplate.queryForObject(sql, Integer.class, params);
    }

    public List<Map<String, Object>> findDataPaged(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql, params);
    }
}
