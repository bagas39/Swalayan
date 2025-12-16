package com.swalayan.repository;

import com.swalayan.models.Barang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BarangRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Barang> findAllAvailable() {
        return jdbcTemplate.query("SELECT id_barang, nama_barang, harga_jual, stok FROM Barang WHERE stok > 0", new BeanPropertyRowMapper<>(Barang.class));
    }

    public Map<String, Object> findById(Integer id) {
        return jdbcTemplate.queryForMap("SELECT harga_jual, stok, nama_barang FROM Barang WHERE id_barang = ?", id);
    }

    public void updateStokBatch(List<Object[]> batchStok) {
        jdbcTemplate.batchUpdate("UPDATE Barang SET stok = stok - ? WHERE id_barang = ?", batchStok);
    }

    public Integer countData(String sql, Object[] params) {
        return jdbcTemplate.queryForObject(sql, Integer.class, params);
    }

    public List<Map<String, Object>> findDataPaged(String sql, Object[] params) {
        return jdbcTemplate.queryForList(sql, params);
    }
}