package org.example.lostarkbackend.domain.markets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketItemStatsResponse {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("AvgPrice")
    private Double avgPrice;    // 평균 거래가

    @JsonProperty("TradeCount")
    private Integer tradeCount; // 판매 건수

    public static MarketItemStatsResponse from(MarketItemPriceHistory history) {
        return new MarketItemStatsResponse(
                history.getPriceDate().toString(),
                history.getAvgPrice(),
                history.getTradeCount()
        );
    }
}
