package org.example.lostarkbackend.domain.markets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MarketItemDetailResponse {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("TradeRemainCount")
    private Integer tradeRemainCount;

    @JsonProperty("BundleCount")
    private Integer bundleCount;

    @JsonProperty("Stats")
    private List<MarketItemStatsResponse> stats;

    @JsonProperty("Tooltip")
    private String toolTip;
}
