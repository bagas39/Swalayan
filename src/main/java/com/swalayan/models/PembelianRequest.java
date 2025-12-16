package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class PembelianRequest {

    @JsonProperty("supplier")
    private String supplier;

    @JsonProperty("idGudang")
    private Integer idGudang;

    @JsonProperty("items")
    private List<ItemPembelian> items;
}