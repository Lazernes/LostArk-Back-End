package org.example.lostarkbackend.domain.auctions.dto.response;

import lombok.Getter;

@Getter
public class AuctionOptionResponse {

    private String Type;                // "ACCESSORY_UPGRADE", "ARK_PASSIVE", 5 등
    private String OptionName;
    private String OptionNameTripod;
    private double Value;
    private boolean IsPenalty;
    private String ClassName;
    private boolean IsValuePercentage;
}
