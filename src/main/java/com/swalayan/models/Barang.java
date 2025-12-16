package com.swalayan.models;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Barang { 
    private Integer idBarang;
    private String namaBarang;
    private String kategori;
    private BigDecimal hargaBeli;
    private BigDecimal hargaJual;
    private Integer stok;
}