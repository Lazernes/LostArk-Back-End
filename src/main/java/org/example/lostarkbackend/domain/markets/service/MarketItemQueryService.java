package org.example.lostarkbackend.domain.markets.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.markets.dao.MarketItemPriceHistoryRepository;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemLatestPriceResponse;
import org.example.lostarkbackend.domain.markets.dto.MarketItemStatsResponse;
import org.example.lostarkbackend.domain.markets.entity.MarketItem;
import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketItemQueryService {

    private final MarketItemRepository marketItemRepository;
    private final MarketItemPriceHistoryRepository marketItemPriceHistoryRepository;

    // 특정 아이템 최신 시세 조회
    @Transactional
    public MarketItemLatestPriceResponse getLatestPrice(Long itemId) {
        MarketItem item = getItemOrThrow(itemId);

        MarketItemPriceHistory latestHistory = marketItemPriceHistoryRepository.findTopByMarketItem_IdOrderByPriceDateDesc(item.getId())
                .orElseThrow(() ->
                        new IllegalStateException("해당 아이템의 시세 데이터가 존재하지 않습니다."));

        return new MarketItemLatestPriceResponse(
                item.getItemId(),
                item.getName(),
                latestHistory.getPriceDate().toString(),
                latestHistory.getAvgPrice(),
                latestHistory.getTradeCount()
        );
    }

    // 기간별 시세 히스토리 조회
    @Transactional
    public List<MarketItemStatsResponse> getPriceHistory(Long itemId, LocalDate startDate, LocalDate endDate) {
        MarketItem item = getItemOrThrow(itemId);

        List<MarketItemPriceHistory> histories = marketItemPriceHistoryRepository.findByMarketItem_IdAndPriceDateBetweenOrderByPriceDate(
                item.getId(), startDate, endDate
        );

        return histories.stream()
                .map(MarketItemStatsResponse::from)
                .toList();
    }

    private MarketItem getItemOrThrow(Long itemId) {
        return marketItemRepository.findByItemId(itemId)
                .orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 아이템입니다. itemId=" + itemId));
    }
}
