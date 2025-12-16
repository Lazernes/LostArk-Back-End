package org.example.lostarkbackend.domain.markets.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.markets.dao.MarketItemPriceHistoryRepository;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemLatestPriceResponse;
import org.example.lostarkbackend.domain.markets.dto.MarketItemPriceSummary;
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

    // 가격 변동 계산
    @Transactional
    public MarketItemPriceSummary calculatePriceChange(Long itemId) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        MarketItemPriceHistory todayHistory = marketItemPriceHistoryRepository.findByMarketItem_IdAndPriceDate(itemId, today)
                .orElse(null);

        MarketItemPriceHistory yesterdayHistory = marketItemPriceHistoryRepository.findByMarketItem_IdAndPriceDate(itemId, yesterday)
                .orElse(null);

        // 오늘 데이터 없으면 계산 불가
        if (todayHistory == null) {
            return new MarketItemPriceSummary(0.0, 0.0, 0.0);
        }

        double todayPrice = todayHistory.getAvgPrice();

        // 어제 데이터 없으면 변동 0 처리
        if (yesterdayHistory == null || yesterdayHistory.getAvgPrice() == 0.0) {
            return new MarketItemPriceSummary(0.0, 0.0, 0.0);
        }

        double yesterdayPrice = yesterdayHistory.getAvgPrice();
        double diff = todayPrice - yesterdayPrice;

        double rate = diff / yesterdayPrice * 100;

        return new MarketItemPriceSummary(todayPrice, Math.round(diff * 10) / 10.0, Math.round(rate * 10) / 10.0);  // rate는 소수점 1자리
    }
}
