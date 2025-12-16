package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty; 
import lombok.Data;
import java.util.List;

@Data
public class TransaksiRequest {
    
    @JsonProperty("idKasir") 
    private Integer idKasir;
    
    @JsonProperty("items")
    private List<ItemKeranjang> items;
}