package com.futbol.Fantasy.repository;

import com.futbol.Fantasy.model.PlayerLeagueFootballer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerLeagueFootballerRepository extends JpaRepository<PlayerLeagueFootballer,Long> {

    List<PlayerLeagueFootballer> findByFootballerId(Long footballerId);

}
