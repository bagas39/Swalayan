package com.swalayan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class TransaksiRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createHeader(Integer idKasir) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO TransaksiPenjualan (id_kasir, total_harga) VALUES (?, 0)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idKasir);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void saveDetailsBatch(List<Object[]> batchDetail) {
        jdbcTemplate.batchUpdate("INSERT INTO DetailPenjualan (id_penjualan, id_barang, jumlah, subtotal) VALUES (?, ?, ?, ?)", batchDetail);
    }

    public void updateHeaderTotal(BigDecimal total, int id) {
        jdbcTemplate.update("UPDATE TransaksiPenjualan SET total_harga = ? WHERE id_penjualan = ?", total, id);
    }

    public void deleteDetailsByHeaderId(int idPenjualan) {
        jdbcTemplate.update("DELETE FROM DetailPenjualan WHERE id_penjualan = ?", idPenjualan);
    }

    public Map<String, Object> findHeaderById(int id) {
        return jdbcTemplate.queryForMap("SELECT tp.*, k.nama_kasir FROM TransaksiPenjualan tp JOIN Kasir k ON tp.id_kasir = k.id_kasir WHERE tp.id_penjualan = ?", id);
    }

    public List<Map<String, Object>> findDetailsById(int id) {
        return jdbcTemplate.queryForList("SELECT dp.id_barang, b.nama_barang, dp.jumlah, dp.subtotal FROM DetailPenjualan dp JOIN Barang b ON dp.id_barang = b.id_barang WHERE dp.id_penjualan = ?", id);
    }

    public Integer countData(String sql, Object[] params) {
        return jdbcTemplate.queryForObject(sql, Integer.class, params);
    }

    public List<Map<String, Object>> findDataPaged(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql, params);
    }

    public Integer getTotalTerjual30Hari(int idBarang) {
        String sql = "SELECT COALESCE(SUM(dp.jumlah), 0) " +
                     "FROM DetailPenjualan dp " +
                     "JOIN TransaksiPenjualan tp ON dp.id_penjualan = tp.id_penjualan " +
                     "WHERE dp.id_barang = ? " +
                     "AND tp.tanggal_penjualan >= DATEADD(day, -30, GETDATE())";
        return jdbcTemplate.queryForObject(sql, Integer.class, idBarang);
    }
}