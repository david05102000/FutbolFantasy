package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.Footballer;
import com.futbol.Fantasy.model.MarketOffer;
import com.futbol.Fantasy.repository.MarketOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarketOfferService {

    @Autowired
    MarketOfferRepository repository;

    public boolean playerHasActiveOffer(Footballer footballer, Long playerId, Long leagueId) {
        return footballer.getMarketOfferList().stream()
                .anyMatch(offer ->
                        offer.getPlayerId().equals(playerId) &&
                                offer.getLeagueId().equals(leagueId) &&
                                !Boolean.TRUE.equals(offer.getExecuted())
                );
    }


    public MarketOffer insert(MarketOffer marketOffer) {
        return repository.save(marketOffer);
    }
}
