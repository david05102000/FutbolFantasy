package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.repository.FootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballerService {
    @Autowired
    FootballerRepository repository;

    public FootballerService() {
    }

    public void insert(Footballer footballer) {
        this.repository.save(footballer);
    }

    public List<Footballer> findAll() {
        return this.repository.findAll();
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }
}