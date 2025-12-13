package org.example.lostarkbackend.domain.markets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketItemLatestPriceResponse {

    @JsonProperty("ItemId")
    private Long itemId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Date")
    private String date;

    @JsonProperty("AvgPrice")
    private Double avgPrice;

    @JsonProperty("TradeCount")
    private Integer tradeCount;
}
