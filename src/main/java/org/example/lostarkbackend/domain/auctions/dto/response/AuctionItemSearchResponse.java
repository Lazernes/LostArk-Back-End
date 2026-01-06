package org.example.lostarkbackend.domain.auctions.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class AuctionItemSearchResponse {

    private int PageNo;
    private int PageSize;
    private int TotalCount;
    private List<AuctionItemResponse> Items;
}
