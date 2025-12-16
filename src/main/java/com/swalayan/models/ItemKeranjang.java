package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty; 
import lombok.Data;

@Data
public class ItemKeranjang {
    
    @JsonProperty("idBarang") 
    private Integer idBarang;
    
    @JsonProperty("jumlah")
    private Integer jumlah;
}