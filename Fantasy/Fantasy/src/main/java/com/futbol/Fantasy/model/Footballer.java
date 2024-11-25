package com.futbol.Fantasy.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "footballer")
public class Footballer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String rol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<MarketOffer> marketOfferList = new ArrayList<MarketOffer>();

    @OneToMany(mappedBy = "footballer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PlayerLeagueFootballer> playerLeagueFootballers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<MarketOffer> getMarketOfferList() {
        return marketOfferList;
    }

    public void setMarketOfferList(List<MarketOffer> marketOfferList) {
        this.marketOfferList = marketOfferList;
    }


    @Override
    public String toString() {
        return name + " | " + rol + " | " + team.getName();
    }
}
