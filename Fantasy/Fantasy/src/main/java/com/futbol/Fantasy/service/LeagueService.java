package com.futbol.Fantasy.service;


import com.futbol.Fantasy.model.League;
import com.futbol.Fantasy.model.Player;
import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.repository.LeagueRepository;
import com.futbol.Fantasy.repository.PlayerLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository repository;

    @Autowired
    PlayerLeagueRepository playerLeagueRepository;


    @Transactional
    public void save(League league) {
        repository.save(league);
    }

    @Transactional
    public List<League> findAllLeaguesByPlayerAndStatus(Long playerId, String status) {
        return repository.findByPlayers_Player_IdAndPlayers_Status(playerId, status);
    }

    public List<League> getAllLeagues() {
        return repository.findAll();
    }

    @Transactional
    public League findLeagueById(Long id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public PlayerLeague saveLeagueWithPlayer(League league, Player player, String status) {
        League savedLeague = repository.save(league);

        PlayerLeague playerLeague = new PlayerLeague(player, savedLeague);
        playerLeague.setStatus(status);
        return playerLeagueRepository.save(playerLeague);

    }


}
