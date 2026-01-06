package org.example.lostarkbackend.domain.auctions.dto.request;

import lombok.Getter;

@Getter
public enum AuctionCategoryCode {
    NECKLACE(200010),
    EARRING(200020),
    RING(200030);

    private final int code;

    AuctionCategoryCode(int code) {
        this.code = code;
    }
}
