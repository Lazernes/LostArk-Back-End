package org.example.lostarkbackend.domain.markets.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.markets.client.MarketApiClient;
import org.example.lostarkbackend.domain.markets.dao.MarketItemPriceHistoryRepository;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemDetailResponse;
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
public class MarketItemService {

    private final MarketItemRepository marketItemRepository;
    private final MarketItemPriceHistoryRepository marketItemPriceHistoryRepository;
    private final MarketApiClient marketApiClient;

    // MarketItem 시세 저장
    @Transactional
    public void saveItemMarketInfo(Long itemId, List<MarketItemDetailResponse> responses) {

        if (responses == null || responses.isEmpty()) {
            log.warn("OpenAPI 응답이 비어있습니다. itemId = {}", itemId);
            return;
        }

        MarketItemDetailResponse dto = pickRealMarketResponse(responses);    // 1번 응답

        MarketItemResult result = findOrCreateMarketItem(itemId, dto);

        savePriceHistory(result.marketItem(), dto.getStats(), result.isNew());

        log.info("MarketItem 저장 완료 itemId={} / name={} / 신규여부={}", itemId, result.marketItem().getName(), result.isNew());
    }

    @Transactional
    public void fetchAndSaveItemMarketInfo(Long itemId) {
        List<MarketItemDetailResponse> responses = marketApiClient.getMarketItemDetail(itemId);

        if (responses == null || responses.isEmpty()) {
            log.warn("Empty response for itemId={}", itemId);
            return;
        }

        saveItemMarketInfo(itemId, responses);
    }

    private MarketItemResult findOrCreateMarketItem(Long itemId, MarketItemDetailResponse dto) {
        return marketItemRepository.findByItemId(itemId)
                .map(existingItem -> new MarketItemResult(existingItem, false))
                .orElseGet(() -> {
                    MarketItem newItem = saveNewMarketItem(itemId, dto);
                    return new MarketItemResult(newItem, true);
                });
    }

    private MarketItem saveNewMarketItem(Long itemId, MarketItemDetailResponse dto) {

        MarketItem item = MarketItem.builder()
                .itemId(itemId)
                .name(dto.getName())
                .tradeRemainCount(dto.getTradeRemainCount())
                .bundleCount(dto.getBundleCount())
                .tooltip(dto.getToolTip())
                .build();

        return marketItemRepository.save(item);
    }

    private void savePriceHistory(MarketItem item, List<MarketItemStatsResponse> stats, boolean isNewItem) {

        if (stats == null || stats.isEmpty()) {
            log.warn("Stats 데이터 없음 itemId={}", item.getItemId());
            return;
        }

        if (isNewItem) {

            LocalDate today = LocalDate.now();

            // 신규 아이템 >> 7일 전체 저장
            for (MarketItemStatsResponse stat : stats) {
                LocalDate date = LocalDate.parse(stat.getDate());

                // 오늘 데이터는 스킵
                if (date.equals(today)) {
                    continue;
                }

                if (marketItemPriceHistoryRepository.existsByMarketItem_IdAndPriceDate(item.getId(), date)) {
                    continue;
                }

                MarketItemPriceHistory history = MarketItemPriceHistory.builder()
                        .marketItem(item)
                        .priceDate(date)
                        .avgPrice(stat.getAvgPrice())
                        .tradeCount(stat.getTradeCount())
                        .build();

                marketItemPriceHistoryRepository.save(history);
            }

            log.info("신규 아이템 14일 시세(오늘 제외) 저장 완료 itemId={}", item.getItemId());
            return;
        }

        // 기존 아이템 >> 어제 데이터만 저장
        MarketItemStatsResponse yesterdayStat = extractYesterdayStat(stats);

        if (yesterdayStat == null) {
            log.warn("어제 날짜 데이터가 없습니다. itemId={}, yesterday={}", item.getItemId(), LocalDate.now().minusDays(1));
            return;
        }

        LocalDate yesterday = LocalDate.parse(yesterdayStat.getDate());

        if (marketItemPriceHistoryRepository.existsByMarketItem_IdAndPriceDate(item.getId(), yesterday)) {
            log.info("이미 저장된 어제 데이터 itemId={}, date={}", item.getItemId(), yesterday);
            return;
        }

        MarketItemPriceHistory history = MarketItemPriceHistory.builder()
                .marketItem(item)
                .priceDate(yesterday)
                .avgPrice(yesterdayStat.getAvgPrice())
                .tradeCount(yesterdayStat.getTradeCount())
                .build();

        marketItemPriceHistoryRepository.save(history);

        log.info("기존 아이템 어제 데이터 저장 완료 itemId={}", item.getItemId());
    }

    // 어제 날짜 데이터만 가져오기
    private MarketItemStatsResponse extractYesterdayStat(List<MarketItemStatsResponse> stats) {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        return stats.stream()
                .filter(s -> LocalDate.parse(s.getDate()).equals(yesterday))
                .findFirst()
                .orElse(null);
    }

    // 실제 데이터를 찾기
    private MarketItemDetailResponse pickRealMarketResponse(List<MarketItemDetailResponse> responses) {

        // Case 1. 각인서: responses.get(1)이 실제 데이터 (TradeRemainCount != null)
        if (responses.size() > 1 && responses.get(1).getTradeRemainCount() != null) {
            return responses.get(1);
        }

        // Case 2. 재련재료: responses.get(0)이 실제 데이터 (TradeRemainCount == null)
        return responses.get(0);
    }
}
