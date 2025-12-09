package org.example.lostarkbackend.domain.markets.dao;

import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MarketItemPriceHistoryRepository extends JpaRepository<MarketItemPriceHistory, Long> {
    boolean existsByMarketItem_IdAndPriceDate(Long itemId, LocalDate priceDate);
}
