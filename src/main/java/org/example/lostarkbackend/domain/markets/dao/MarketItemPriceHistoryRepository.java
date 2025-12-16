package org.example.lostarkbackend.domain.markets.dao;

import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MarketItemPriceHistoryRepository extends JpaRepository<MarketItemPriceHistory, Long> {
    boolean existsByMarketItem_IdAndPriceDate(Long itemId, LocalDate priceDate);

    Optional<MarketItemPriceHistory> findTopByMarketItem_IdOrderByPriceDateDesc(Long itemId);

    List<MarketItemPriceHistory> findByMarketItem_IdAndPriceDateBetweenOrderByPriceDate(Long itemId, LocalDate startDate, LocalDate endDate);

    Optional<MarketItemPriceHistory> findByMarketItem_IdAndPriceDate(Long itemId, LocalDate priceDate);
}
