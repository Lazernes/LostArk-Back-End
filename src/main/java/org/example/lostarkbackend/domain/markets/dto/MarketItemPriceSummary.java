package org.example.lostarkbackend.domain.markets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MarketItemPriceSummary {

    private double currentPrice;
    private double priceChange; // today - yesterday
    private double changeRate;
}
