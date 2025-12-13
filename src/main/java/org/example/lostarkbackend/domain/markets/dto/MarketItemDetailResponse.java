package org.example.lostarkbackend.domain.markets.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @JsonProperty("ToolTip")
    private String toolTip;
}
