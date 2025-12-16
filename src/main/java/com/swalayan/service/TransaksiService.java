package com.swalayan.service;

import com.swalayan.models.ItemKeranjang;
import com.swalayan.models.TransaksiRequest;
import com.swalayan.repository.BarangRepository;
import com.swalayan.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepo;
    
    @Autowired
    private BarangRepository barangRepo;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(TransaksiRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) throw new IllegalArgumentException("Keranjang kosong");
        
        Integer idKasir = request.getIdKasir() != null ? request.getIdKasir() : 1;
        int idPenjualan = transaksiRepo.createHeader(idKasir);

        BigDecimal totalHarga = processTransactionItems(idPenjualan, request.getItems());
        
        transaksiRepo.updateHeaderTotal(totalHarga, idPenjualan);

        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("id_penjualan", idPenjualan);
        resp.put("total_harga", totalHarga);
        return resp;
    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> edit(int idPenjualan, TransaksiRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) throw new IllegalArgumentException("Item tidak boleh kosong");

        List<Map<String, Object>> oldItems = transaksiRepo.findDetailsById(idPenjualan);
        List<Object[]> restoreStockBatch = new ArrayList<>();

        for (Map<String, Object> item : oldItems) {
            Integer idBarang = (Integer) item.get("id_barang");
            Integer jumlahLama = (Integer) item.get("jumlah");

            restoreStockBatch.add(new Object[]{-jumlahLama, idBarang});
        }
        barangRepo.updateStokBatch(restoreStockBatch);

        transaksiRepo.deleteDetailsByHeaderId(idPenjualan);

        BigDecimal totalHarga = processTransactionItems(idPenjualan, request.getItems());

        transaksiRepo.updateHeaderTotal(totalHarga, idPenjualan);

        return Map.of("success", true, "message", "Transaksi berhasil diperbarui");
    }


    private BigDecimal processTransactionItems(int idPenjualan, List<ItemKeranjang> items) {
        BigDecimal totalHarga = BigDecimal.ZERO;
        List<Object[]> batchDetail = new ArrayList<>();
        List<Object[]> batchStok = new ArrayList<>();

        for (ItemKeranjang item : items) {

            try {
                Map<String, Object> brg = barangRepo.findById(item.getIdBarang());
                
                BigDecimal harga = (BigDecimal) brg.get("harga_jual");
                Integer stok = (Integer) brg.get("stok");
                String namaBarang = (String) brg.get("nama_barang");

                if (stok < item.getJumlah()) {
                    throw new RuntimeException("Stok '" + namaBarang + "' tidak cukup (Sisa: " + stok + ")");
                }
                
                BigDecimal subtotal = harga.multiply(new BigDecimal(item.getJumlah()));
                totalHarga = totalHarga.add(subtotal);

                batchDetail.add(new Object[]{idPenjualan, item.getIdBarang(), item.getJumlah(), subtotal});
                batchStok.add(new Object[]{item.getJumlah(), item.getIdBarang()});
                
            } catch (org.springframework.dao.EmptyResultDataAccessException e) {
                throw new RuntimeException("Barang dengan ID " + item.getIdBarang() + " tidak ditemukan di database.");
            }
        }

        transaksiRepo.saveDetailsBatch(batchDetail);
        barangRepo.updateStokBatch(batchStok);
        
        return totalHarga;
    }

    public Map<String, Object> getHistoryPaged(int offset, int limit, String searchId, String startDate, String endDate) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();

        if (searchId != null && !searchId.isEmpty()) { where.append(" AND CAST(tp.id_penjualan AS VARCHAR) LIKE ?"); args.add("%" + searchId + "%"); }
        if (startDate != null && !startDate.isEmpty()) { where.append(" AND tp.tanggal_penjualan >= ?"); args.add(startDate); }
        if (endDate != null && !endDate.isEmpty()) { where.append(" AND tp.tanggal_penjualan < DATEADD(day, 1, CAST(? AS DATE))"); args.add(endDate); }

        String sqlCount = "SELECT COUNT(*) FROM TransaksiPenjualan tp JOIN Kasir k ON tp.id_kasir = k.id_kasir " + where;
        Integer total = transaksiRepo.countData(sqlCount, args.toArray());

        String sqlData = "WITH Paged AS (SELECT tp.id_penjualan, tp.tanggal_penjualan, tp.total_harga, k.nama_kasir, ROW_NUMBER() OVER(ORDER BY tp.tanggal_penjualan DESC) as rn FROM TransaksiPenjualan tp JOIN Kasir k ON tp.id_kasir = k.id_kasir " + where + ") SELECT * FROM Paged WHERE rn > ? AND rn <= ?";
        args.add(offset); args.add(offset + limit);

        return Map.of("transactions", transaksiRepo.findDataPaged(sqlData, args.toArray()), "totalAvailableTransactions", total);
    }

    public Map<String, Object> getDetail(int id) {
        Map<String, Object> header = transaksiRepo.findHeaderById(id);
        List<Map<String, Object>> items = transaksiRepo.findDetailsById(id);
        BigDecimal total = (BigDecimal) header.get("total_harga");
        BigDecimal subtotal = items.stream().map(i -> (BigDecimal) i.get("subtotal")).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of("header", header, "items", items, "summary", Map.of("subtotal", subtotal, "pajak", total.subtract(subtotal), "total", total));
    }
}