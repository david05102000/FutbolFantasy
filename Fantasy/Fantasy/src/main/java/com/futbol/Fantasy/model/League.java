package com.futbol.Fantasy.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "league"
)
public class League {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column
    private String name;
    @OneToMany(
            mappedBy = "league",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST},
            orphanRemoval = true
    )
    private List<PlayerLeague> players = new ArrayList();
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    private List<Footballer> footballersMarket = new ArrayList();

    public League() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getLeagueNameCode() {
        String var10000 = this.name;
        return var10000 + " #" + this.getId();
    }

    public List<PlayerLeague> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<PlayerLeague> players) {
        this.players = players;
    }

    public List<Footballer> getFootballersMarket() {
        return this.footballersMarket;
    }

    public void setFootballersMarket(List<Footballer> footballersMarket) {
        this.footballersMarket = footballersMarket;
    }
}
