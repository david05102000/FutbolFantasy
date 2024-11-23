package com.futbol.Fantasy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player_league_footballers")
public class PlayerLeagueFootballer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long playerLeagueFootballerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_league_id")
    private PlayerLeague playerLeague;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Footballer footballer;

    @Column(name = "selected")
    private Boolean selected;

    public PlayerLeague getPlayerLeague() {
        return playerLeague;
    }

    public void setPlayerLeague(PlayerLeague playerLeague) {
        this.playerLeague = playerLeague;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getPlayerLeagueFootballerId() {
        return playerLeagueFootballerId;
    }

    public void setPlayerLeagueFootballerId(Long playerLeagueFootballerId) {
        this.playerLeagueFootballerId = playerLeagueFootballerId;
    }
}
