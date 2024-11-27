package com.futbol.Fantasy.repository;

import com.futbol.Fantasy.model.MarketOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketOfferRepository extends JpaRepository<MarketOffer, Long> {

}
