package org.example.lostarkbackend.domain.auctions.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class AuctionItemResponse {

    private String Name;
    private String Grade;
    private int Tier;
    private int Level;
    private String Icon;
    private int GradeQuality;

    private AuctionInfoResponse AuctionInfo;
    private List<AuctionOptionResponse> Options;
}
