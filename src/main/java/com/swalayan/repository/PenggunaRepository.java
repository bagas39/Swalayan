package com.swalayan.repository;

import com.swalayan.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PenggunaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserDTO> findAllUsers() {
        String sql = "SELECT id_kasir AS id, nama_kasir AS nama, username, 'Kasir' AS role FROM Kasir " +
                     "UNION ALL " +
                     "SELECT id_gudang, nama_gudang, username, 'Gudang' FROM PenjagaGudang " +
                     "UNION ALL " +
                     "SELECT id_supervisor, nama_supervisor, username, 'Supervisor' FROM Supervisor " +
                     "UNION ALL " +
                     "SELECT id_owner, nama_owner, username, 'Owner' FROM Owner";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDTO.class));
    }

    public UserDTO findByUsername(String username) {
        String sql = "SELECT * FROM (" +
                     "  SELECT id_kasir AS id, nama_kasir AS nama, username, password, 'Kasir' AS role FROM Kasir " +
                     "  UNION ALL " +
                     "  SELECT id_gudang, nama_gudang, username, password, 'Gudang' AS role FROM PenjagaGudang " +
                     "  UNION ALL " +
                     "  SELECT id_supervisor, nama_supervisor, username, password, 'Supervisor' AS role FROM Supervisor " +
                     "  UNION ALL " +
                     "  SELECT id_owner, nama_owner, username, password, 'Owner' AS role FROM Owner" +
                     ") AS AllUsers WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDTO.class), username);
        } catch (Exception e) {
            return null;
        }
    }

    public void save(UserDTO user) {
        String sql;
        switch (user.getRole()) {
            case "Kasir":
                sql = "INSERT INTO Kasir (nama_kasir, username, password, no_hp) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword(), user.getNoHp());
                break;
            case "Gudang":
                sql = "INSERT INTO PenjagaGudang (nama_gudang, username, password) VALUES (?, ?, ?)";
                jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword());
                break;
            case "Supervisor":
                sql = "INSERT INTO Supervisor (nama_supervisor, username, password) VALUES (?, ?, ?)";
                jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword());
                break;
            case "Owner":
                sql = "INSERT INTO Owner (nama_owner, username, password) VALUES (?, ?, ?)";
                jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword());
                break;
            default:
                throw new IllegalArgumentException("Role tidak valid");
        }
    }


    public UserDTO findById(Integer id, String role) {
        String sql;
        switch (role) {
            case "Kasir":
                sql = "SELECT id_kasir AS id, nama_kasir AS nama, username, 'Kasir' AS role, no_hp AS noHp FROM Kasir WHERE id_kasir = ?";
                break;
            case "Gudang":
                sql = "SELECT id_gudang AS id, nama_gudang AS nama, username, 'Gudang' AS role FROM PenjagaGudang WHERE id_gudang = ?";
                break;
            case "Supervisor":
                sql = "SELECT id_supervisor AS id, nama_supervisor AS nama, username, 'Supervisor' AS role FROM Supervisor WHERE id_supervisor = ?";
                break;
            case "Owner":
                sql = "SELECT id_owner AS id, nama_owner AS nama, username, 'Owner' AS role FROM Owner WHERE id_owner = ?";
                break;
            default:
                return null;
        }
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserDTO.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(UserDTO user) {
        String sql;

        boolean updatePassword = user.getPassword() != null && !user.getPassword().isEmpty();

        switch (user.getRole()) {
            case "Kasir":
                if (updatePassword) {
                    sql = "UPDATE Kasir SET nama_kasir=?, username=?, password=?, no_hp=? WHERE id_kasir=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword(), user.getNoHp(), user.getId());
                } else {
                    sql = "UPDATE Kasir SET nama_kasir=?, username=?, no_hp=? WHERE id_kasir=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getNoHp(), user.getId());
                }
                break;
            case "Gudang":
                if (updatePassword) {
                    sql = "UPDATE PenjagaGudang SET nama_gudang=?, username=?, password=? WHERE id_gudang=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword(), user.getId());
                } else {
                    sql = "UPDATE PenjagaGudang SET nama_gudang=?, username=? WHERE id_gudang=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getId());
                }
                break;
            case "Supervisor":
                if (updatePassword) {
                    sql = "UPDATE Supervisor SET nama_supervisor=?, username=?, password=? WHERE id_supervisor=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword(), user.getId());
                } else {
                    sql = "UPDATE Supervisor SET nama_supervisor=?, username=? WHERE id_supervisor=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getId());
                }
                break;
            case "Owner":
                if (updatePassword) {
                    sql = "UPDATE Owner SET nama_owner=?, username=?, password=? WHERE id_owner=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getPassword(), user.getId());
                } else {
                    sql = "UPDATE Owner SET nama_owner=?, username=? WHERE id_owner=?";
                    jdbcTemplate.update(sql, user.getNama(), user.getUsername(), user.getId());
                }
                break;
        }
    }

    public void delete(Integer id, String role) {
        String sql;
        switch (role) {
            case "Kasir":      sql = "DELETE FROM Kasir WHERE id_kasir = ?"; break;
            case "Gudang":     sql = "DELETE FROM PenjagaGudang WHERE id_gudang = ?"; break;
            case "Supervisor": sql = "DELETE FROM Supervisor WHERE id_supervisor = ?"; break;
            case "Owner":      sql = "DELETE FROM Owner WHERE id_owner = ?"; break;
            default: return;
        }
        jdbcTemplate.update(sql, id);
    }
}