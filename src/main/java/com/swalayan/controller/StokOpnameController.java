package com.swalayan.controller;

import com.swalayan.models.StokOpname;
import com.swalayan.service.StokOpnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/opname")
@CrossOrigin(origins = "*") 
public class StokOpnameController {

    @Autowired
    private StokOpnameService service;


    @GetMapping
    public List<StokOpname> getAllOpname() {
        return service.getAllOpname();
    }

    @PostMapping
    public String createOpname(@RequestBody Map<String, Integer> payload) {
 
        service.simpanOpname(
            payload.get("idGudang"),
            payload.get("idBarang"),
            payload.get("stokSistem"),
            payload.get("stokFisik")
        );


        return "Data Opname Berhasil Disimpan!";
    }
}