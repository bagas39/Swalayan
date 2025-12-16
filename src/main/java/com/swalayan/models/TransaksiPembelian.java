package com.swalayan.models;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransaksiPembelian {
    private Integer idPembelian;
    private LocalDateTime tanggalPembelian;
    private String supplier;
    private BigDecimal totalBeli;
    private Integer idGudang;
    private List<ItemPembelian> items;
}