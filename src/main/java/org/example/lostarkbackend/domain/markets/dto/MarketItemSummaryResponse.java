package org.example.lostarkbackend.domain.markets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MarketItemSummaryResponse {

    private Long itemId;
    private String name;
    private String grade;
    private int bundleCount;
    private String iconUrl;
    private String category;

    private double currentPrice;
    private double priceChange;
    private double changeRate;
}
