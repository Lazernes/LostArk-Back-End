package org.example.lostarkbackend.domain.markets.controller;

import lombok.RequiredArgsConstructor;
import org.example.lostarkbackend.domain.markets.dto.MarketItemLatestPriceResponse;
import org.example.lostarkbackend.domain.markets.dto.MarketItemStatsResponse;
import org.example.lostarkbackend.domain.markets.service.MarketItemCollectService;
import org.example.lostarkbackend.domain.markets.service.MarketItemQueryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class MarketItemController {

    private final MarketItemCollectService marketItemCollectService;
    private final MarketItemQueryService marketItemQueryService;

    @GetMapping("/fetch/{itemId}")
    public ResponseEntity<String> fetchItem(@PathVariable Long itemId) {
        marketItemCollectService.fetchAndSaveItemMarketInfo(itemId);
        return ResponseEntity.ok("Market item saved: " + itemId);
    }

    // 특정 아이템 최신 시세 조회
    @GetMapping("/{itemId}/latest")
    public MarketItemLatestPriceResponse getLatestPrice(@PathVariable Long itemId) {
        return marketItemQueryService.getLatestPrice(itemId);
    }

    // 기간별 시세 히스토리 조회
    @GetMapping("/{itemId}/history")
    public List<MarketItemStatsResponse> getPriceHistory(@PathVariable Long itemId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return marketItemQueryService.getPriceHistory(itemId, startDate, endDate);
    }
}
