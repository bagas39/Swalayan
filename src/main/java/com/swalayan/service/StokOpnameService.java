package com.swalayan.service;

import com.swalayan.models.StokOpname;
import com.swalayan.repository.StokOpnameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StokOpnameService {

    @Autowired
    private StokOpnameRepository repository;

    public List<StokOpname> getAllOpname() {
        return repository.findAll(); 
    }

    public void simpanOpname(Integer idGudang, Integer idBarang, Integer stokSistem, Integer stokFisik) {
        StokOpname opnameBaru = new StokOpname(idGudang, idBarang, stokSistem, stokFisik);
        repository.save(opnameBaru); 
    }
}