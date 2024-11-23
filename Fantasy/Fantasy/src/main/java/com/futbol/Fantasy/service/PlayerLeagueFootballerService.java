package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import com.futbol.Fantasy.repository.PlayerLeagueFootballerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerLeagueFootballerService {

    @Autowired
    PlayerLeagueFootballerRepository repository;

    public void save(PlayerLeagueFootballer playerLeagueFootballer) {
        repository.save(playerLeagueFootballer);
    }

    public Optional<PlayerLeagueFootballer> findById(Long id) {
        return repository.findById(id);
    }


    public List<PlayerLeagueFootballer> findByFootballerId(Long id) {
        return repository.findByFootballerId(id);
    }

}
