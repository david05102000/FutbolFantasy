package com.futbol.Fantasy.repository;

import com.futbol.Fantasy.model.PlayerLeague;
import com.futbol.Fantasy.model.PlayerLeagueId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerLeagueRepository extends JpaRepository<PlayerLeague, PlayerLeagueId> {
    List<PlayerLeague> findByLeagueId(Long leagueId);

    PlayerLeague findByLeagueIdAndPlayerId(Long leagueId, Long playerId);
}
