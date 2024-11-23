package com.futbol.Fantasy.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "player_league")
public class PlayerLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    private League league;

    @OneToMany(mappedBy = "playerLeague", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PlayerLeagueFootballer> playerLeagueFootballers = new ArrayList<>();

    @Column(name = "points")
    private int points;

    @Column
    private String status;

    @Column(name = "money")
    private int money;

    public PlayerLeague() {
    }

    public PlayerLeague(Player player, League league, int points) {
        this.player = player;
        this.league = league;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerLeague(Player player, League league) {
        this.player = player;
        this.league = league;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlayerLeagueFootballer> getPlayerLeagueFootballers() {
        return playerLeagueFootballers;
    }

    public void setPlayerLeagueFootballers(List<PlayerLeagueFootballer> playerLeagueFootballers) {
        this.playerLeagueFootballers = playerLeagueFootballers;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}


