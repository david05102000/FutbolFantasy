package com.futbol.Fantasy.model;

import java.io.Serializable;

public class PlayerLeagueFootballerId implements Serializable {

    private Long playerLeague;
    private Long footballer;

    public PlayerLeagueFootballerId() {
    }

    public PlayerLeagueFootballerId(Long playerLeague, Long footballer) {
        this.playerLeague = playerLeague;
        this.footballer = footballer;
    }

    public Long getPlayerLeague() {
        return playerLeague;
    }

    public void setPlayerLeague(Long playerLeague) {
        this.playerLeague = playerLeague;
    }

    public Long getFootballer() {
        return footballer;
    }

    public void setFootballer(Long footballer) {
        this.footballer = footballer;
    }
}




