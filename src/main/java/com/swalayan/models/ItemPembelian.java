package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemPembelian {
    
    @JsonProperty("idBarang")
    private Integer idBarang;

    @JsonProperty("namaBarang")
    private String namaBarang;

    @JsonProperty("hargaBeli")
    private BigDecimal hargaBeli;

    @JsonProperty("jumlah")
    private Integer jumlah;
}