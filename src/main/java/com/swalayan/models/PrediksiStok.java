package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrediksiStok {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("barangId")
    private Long barangId;
    
    @JsonProperty("stokSaatIni")
    private int stokSaatIni;

    @JsonProperty("prediksiKebutuhan")
    private int prediksiKebutuhan; 

    @JsonProperty("hariBertahan")
    private int hariBertahan; 

    @JsonProperty("tanggalPrediksi")
    private String tanggalPrediksi;

    public PrediksiStok() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBarangId() { return barangId; }
    public void setBarangId(Long barangId) { this.barangId = barangId; }

    public int getStokSaatIni() { return stokSaatIni; }
    public void setStokSaatIni(int stokSaatIni) { this.stokSaatIni = stokSaatIni; }

    public int getPrediksiKebutuhan() { return prediksiKebutuhan; }
    public void setPrediksiKebutuhan(int prediksiKebutuhan) { this.prediksiKebutuhan = prediksiKebutuhan; }

    public int getHariBertahan() { return hariBertahan; }
    public void setHariBertahan(int hariBertahan) { this.hariBertahan = hariBertahan; }

    public String getTanggalPrediksi() { return tanggalPrediksi; }
    public void setTanggalPrediksi(String tanggalPrediksi) { this.tanggalPrediksi = tanggalPrediksi; }
}