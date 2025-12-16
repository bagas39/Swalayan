package com.swalayan.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LaporanKeuangan {


    
    @JsonProperty("totalPendapatan")
    private double totalPendapatan;

    @JsonProperty("totalPengeluaran")
    private double totalPengeluaran;

    @JsonProperty("labaBersih")
    private double labaBersih;

    @JsonProperty("periode")
    private String periode;

    public LaporanKeuangan() {}

    public LaporanKeuangan(double totalPendapatan, double totalPengeluaran, String periode) {
        this.totalPendapatan = totalPendapatan;
        this.totalPengeluaran = totalPengeluaran;
        this.periode = periode;
        this.labaBersih = totalPendapatan - totalPengeluaran;
    }

    // --- GETTER (Tetap Wajib) ---

    public double getTotalPendapatan() {
        return totalPendapatan;
    }

    public double getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public double getLabaBersih() {
        return labaBersih;
    }

    public String getPeriode() {
        return periode;
    }

    public void setTotalPendapatan(double totalPendapatan) {
        this.totalPendapatan = totalPendapatan;
    }
    
    public void setTotalPengeluaran(double totalPengeluaran) {
        this.totalPengeluaran = totalPengeluaran;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
}