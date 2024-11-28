package com.futbol.Fantasy.table.model;

import com.futbol.Fantasy.model.Footballer;

public class MarketAdd {

    private Footballer footballer;

    private Long leagueId;

    private Long playerId;

    private int amount;

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MarketAdd(Long leagueId, Long playerId, int amount, Footballer footballer) {
        this.leagueId = leagueId;
        this.playerId = playerId;
        this.amount = amount;
        this.footballer = footballer;
    }

    public MarketAdd(Long leagueId, Long playerId, int amount) {
        this.leagueId = leagueId;
        this.playerId = playerId;
        this.amount = amount;
    }

    public MarketAdd() {
    }
}
