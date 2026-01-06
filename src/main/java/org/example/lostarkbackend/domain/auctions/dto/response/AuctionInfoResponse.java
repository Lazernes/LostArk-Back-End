package org.example.lostarkbackend.domain.auctions.dto.response;

import lombok.Getter;

@Getter
public class AuctionInfoResponse {

    private int StartPrice;
    private int BuyPrice;
    private int BidPrice;
    private String EndDate;
    private int BidCount;
    private int BidStartPrice;
    private boolean IsCompetitive;
    private int TradeAllowCount;
    private int UpgradeLevel;
}
