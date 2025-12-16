package com.swalayan.models; 

import java.time.LocalDateTime;

public class StokOpname {

    private Integer idOpname;
    private LocalDateTime tanggalOpname = LocalDateTime.now();
    private Integer idGudang;
    private Integer idBarang;
    private Integer stokSistem;
    private Integer stokFisik;
    private Integer selisih;
    private String statusVerifikasi = "Pending";
    private Integer idSupervisor;
    private LocalDateTime tanggalVerifikasi;

    public StokOpname() {}

    public StokOpname(Integer idGudang, Integer idBarang, Integer stokSistem, Integer stokFisik) {
        this.idGudang = idGudang;
        this.idBarang = idBarang;
        this.stokSistem = stokSistem;
        this.stokFisik = stokFisik;
        this.statusVerifikasi = "Pending";
        this.tanggalOpname = LocalDateTime.now();
    }

    public Integer getIdOpname() { return idOpname; }
    public void setIdOpname(Integer idOpname) { this.idOpname = idOpname; }

    public LocalDateTime getTanggalOpname() { return tanggalOpname; }
    public void setTanggalOpname(LocalDateTime tanggalOpname) { this.tanggalOpname = tanggalOpname; }

    public Integer getIdGudang() { return idGudang; }
    public void setIdGudang(Integer idGudang) { this.idGudang = idGudang; }

    public Integer getIdBarang() { return idBarang; }
    public void setIdBarang(Integer idBarang) { this.idBarang = idBarang; }

    public Integer getStokSistem() { return stokSistem; }
    public void setStokSistem(Integer stokSistem) { this.stokSistem = stokSistem; }

    public Integer getStokFisik() { return stokFisik; }
    public void setStokFisik(Integer stokFisik) { this.stokFisik = stokFisik; }

    // Hitung selisih manual di sini
    public Integer getSelisih() { 
        if (stokFisik != null && stokSistem != null) {
            return stokFisik - stokSistem; 
        }
        return 0;
    }
    public void setSelisih(Integer selisih) { this.selisih = selisih; }

    public String getStatusVerifikasi() { return statusVerifikasi; }
    public void setStatusVerifikasi(String statusVerifikasi) { this.statusVerifikasi = statusVerifikasi; }

    public Integer getIdSupervisor() { return idSupervisor; }
    public void setIdSupervisor(Integer idSupervisor) { this.idSupervisor = idSupervisor; }

    public LocalDateTime getTanggalVerifikasi() { return tanggalVerifikasi; }
    public void setTanggalVerifikasi(LocalDateTime tanggalVerifikasi) { this.tanggalVerifikasi = tanggalVerifikasi; }
}