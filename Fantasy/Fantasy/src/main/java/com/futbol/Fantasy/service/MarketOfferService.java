package com.futbol.Fantasy.service;

import com.futbol.Fantasy.model.MarketOffer;
import com.futbol.Fantasy.repository.MarketOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketOfferService {

    @Autowired
    MarketOfferRepository repository;

    public MarketOffer insert(MarketOffer marketOffer) {
        return repository.save(marketOffer);
    }
}
