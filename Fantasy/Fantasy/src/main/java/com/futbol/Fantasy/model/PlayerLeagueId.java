package com.futbol.Fantasy.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;



public class PlayerLeagueId implements Serializable {
    private Long player;
    private Long league;

    public PlayerLeagueId(Long player, Long league) {
        this.player = player;
        this.league = league;
    }

    public PlayerLeagueId() {

    }

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long player) {
        this.player = player;
    }

    public Long getLeague() {
        return league;
    }

    public void setLeague(Long league) {
        this.league = league;
    }
}
