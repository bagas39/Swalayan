package com.swalayan.service;

import com.swalayan.models.ItemPembelian;
import com.swalayan.models.PembelianRequest;
import com.swalayan.repository.PembelianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PembelianService {

    @Autowired
    private PembelianRepository pembelianRepo;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createPembelian(PembelianRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) throw new IllegalArgumentException("Item kosong.");
        if (request.getIdGudang() == null) throw new IllegalArgumentException("ID Gudang kosong.");
        if (request.getSupplier() == null || request.getSupplier().isEmpty()) throw new IllegalArgumentException("Supplier kosong.");

        int idPembelian = pembelianRepo.createHeader(request.getSupplier(), request.getIdGudang());
        BigDecimal totalBeli = BigDecimal.ZERO;

        List<Object[]> batchDetail = new ArrayList<>();
        List<Object[]> batchStok = new ArrayList<>();

        for (ItemPembelian item : request.getItems()) {
            if (item.getHargaBeli() == null || item.getJumlah() == null) {
                throw new IllegalArgumentException("Harga/Jumlah tidak valid.");
            }

            Integer finalIdBarang;

            Integer existingId = pembelianRepo.findIdBarangByName(item.getNamaBarang());

            if (existingId != null) {

                finalIdBarang = existingId;
            } else {

                BigDecimal hargaJual = item.getHargaBeli().multiply(new BigDecimal("1.3"));
                finalIdBarang = pembelianRepo.createBarangBaru(
                    item.getNamaBarang(), 
                    item.getHargaBeli(), 
                    hargaJual
                );
            }

            BigDecimal subtotal = item.getHargaBeli().multiply(new BigDecimal(item.getJumlah()));
            totalBeli = totalBeli.add(subtotal);

            batchDetail.add(new Object[]{ idPembelian, finalIdBarang, item.getJumlah(), item.getHargaBeli(), subtotal });
            batchStok.add(new Object[]{ item.getJumlah(), finalIdBarang });
        }

        pembelianRepo.saveDetailsBatch(batchDetail);
        pembelianRepo.updateStokBatch(batchStok);
        pembelianRepo.updateHeaderTotal(totalBeli, idPembelian);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("id_pembelian", idPembelian);
        response.put("message", "Transaksi berhasil.");
        return response;
    }

    public Map<String, Object> getHistoryPembelian(int offset, int limit, String searchSupplier, String startDate, String endDate) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();

        if (searchSupplier != null && !searchSupplier.isEmpty()) {
            where.append(" AND supplier_name LIKE ?");
            args.add("%" + searchSupplier + "%");
        }
        if (startDate != null && !startDate.isEmpty()) {
            where.append(" AND tanggal_pembelian >= ?");
            args.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            where.append(" AND tanggal_pembelian < DATEADD(day, 1, CAST(? AS DATE))");
            args.add(endDate);
        }

        String sqlCount = "SELECT COUNT(*) FROM TransaksiPembelian " + where;
        Integer total = pembelianRepo.countData(sqlCount, args.toArray());

        String sqlData = "WITH Paged AS (" +
                "  SELECT id_pembelian, tanggal_pembelian, supplier_name, total_pembelian, " +
                "  ROW_NUMBER() OVER(ORDER BY tanggal_pembelian DESC) as rn " +
                "  FROM TransaksiPembelian " + where + 
                ") " +
                "SELECT id_pembelian, tanggal_pembelian, supplier_name AS supplier, total_pembelian AS total_beli " +
                "FROM Paged WHERE rn > ? AND rn <= ?";
        
        args.add(offset);
        args.add(offset + limit);

        return Map.of(
            "purchases", pembelianRepo.findDataPaged(sqlData, args.toArray()),
            "totalAvailablePurchases", total != null ? total : 0
        );
    }
}