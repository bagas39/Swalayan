package com.swalayan.service;

import com.swalayan.models.Barang;
import com.swalayan.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BarangService {

    @Autowired
    private BarangRepository barangRepository;

    public List<Barang> getAllForKasir() {
        return barangRepository.findAllAvailable();
    }

    public Map<String, Object> getListStokPaged(int offset, int limit, String searchNama) {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> args = new ArrayList<>();
        
        if (searchNama != null && !searchNama.isEmpty()) {
            where.append(" AND (nama_barang LIKE ? OR CAST(id_barang AS VARCHAR) LIKE ?)");
            args.add("%" + searchNama + "%");
            args.add("%" + searchNama + "%");
        }

        String sqlCount = "SELECT COUNT(*) FROM Barang " + where;
        Integer total = barangRepository.countData(sqlCount, args.toArray());

        String sqlData = "WITH Paged AS (SELECT *, ROW_NUMBER() OVER(ORDER BY id_barang) as rn FROM Barang " + where + ") SELECT * FROM Paged WHERE rn > ? AND rn <= ?";
        args.add(offset);
        args.add(offset + limit);

        return Map.of("items", barangRepository.findDataPaged(sqlData, args.toArray()), "totalAvailableItems", total);
    }
}