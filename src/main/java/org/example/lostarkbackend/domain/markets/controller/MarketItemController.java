package org.example.lostarkbackend.domain.markets.controller;

import lombok.RequiredArgsConstructor;
import org.example.lostarkbackend.domain.markets.service.MarketItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/market")
public class MarketItemController {

    private final MarketItemService marketItemService;

    @GetMapping("/fetch/{itemId}")
    public ResponseEntity<String> fetchItem(@PathVariable Long itemId) {
        marketItemService.fetchAndSaveItemMarketInfo(itemId);
        return ResponseEntity.ok("Market item saved: " + itemId);
    }
}
