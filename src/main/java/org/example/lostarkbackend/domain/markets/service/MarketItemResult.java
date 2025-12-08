package org.example.lostarkbackend.domain.markets.service;

import org.example.lostarkbackend.domain.markets.entity.MarketItem;

public record MarketItemResult(MarketItem marketItem, boolean isNew) {

}
