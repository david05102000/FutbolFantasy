package com.futbol.Fantasy.repository;

import com.futbol.Fantasy.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    List<League> findByPlayers_Player_IdAndPlayers_Status(Long id, String status);
}
