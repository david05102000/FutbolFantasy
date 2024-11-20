package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.repository.PlayerLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerLeagueService {
    @Autowired
    PlayerLeagueRepository repository;

    public PlayerLeagueService() {
    }

    @Transactional
    public List<PlayerLeague> findByLeagueId(Long id) {
        return this.repository.findByLeagueId(id);
    }

    @Transactional
    public PlayerLeague findByLeagueIdAndPlayerId(Long leagueId, Long playerId) {
        return this.repository.findByLeagueIdAndPlayerId(leagueId, playerId);
    }

    public void save(PlayerLeague playerLeague) {
        this.repository.save(playerLeague);
    }

    public void delete(PlayerLeague playerLeague) {
        this.repository.delete(playerLeague);
    }
}
