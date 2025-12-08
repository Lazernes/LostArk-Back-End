package org.example.lostarkbackend.domain.markets.dao;

import org.example.lostarkbackend.domain.markets.entity.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketItemRepository extends JpaRepository<MarketItem, Long> {
    Optional<MarketItem> findByItemId(Long itemId);
}