package com.swalayan.repository;

import com.swalayan.models.StokOpname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StokOpnameRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(StokOpname opname) {
        String sql = "UPDATE Barang SET stok = ? WHERE id_barang = ?";
        
        return jdbcTemplate.update(sql, 
            opname.getStokFisik(), 
            opname.getIdBarang()   
        );
    }

   
    public List<StokOpname> findAll() {
        String sql = "SELECT * FROM StokOpname";
        
       
        return jdbcTemplate.query(sql, new RowMapper<StokOpname>() {
            @Override
            public StokOpname mapRow(ResultSet rs, int rowNum) throws SQLException {
                StokOpname so = new StokOpname();
                so.setIdOpname(rs.getInt("id_opname"));
                so.setIdGudang(rs.getInt("id_gudang"));
                so.setIdBarang(rs.getInt("id_barang"));
                so.setStokSistem(rs.getInt("stok_sistem"));
                so.setStokFisik(rs.getInt("stok_fisik"));
                so.setStatusVerifikasi(rs.getString("status_verifikasi"));
                return so;
            }
        });
    }
}