package org.example.lostarkbackend.domain.markets.service;

import jakarta.transaction.Transactional;
import org.example.lostarkbackend.domain.markets.client.MarketApiClient;
import org.example.lostarkbackend.domain.markets.dao.MarketItemPriceHistoryRepository;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemDetailResponse;
import org.example.lostarkbackend.domain.markets.dto.MarketItemStatsResponse;
import org.example.lostarkbackend.domain.markets.entity.MarketItem;
import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@Transactional
class MarketItemCollectServiceTest {

    @Autowired
    private MarketItemCollectService marketItemCollectService;

    @MockitoBean
    private MarketItemRepository marketItemRepository;

    @MockitoBean
    private MarketItemPriceHistoryRepository marketItemPriceHistoryRepository;

    @MockitoBean
    private MarketApiClient marketApiClient;

    @Test
    @DisplayName("신규 아이템이면 14일 데이터 중에서 오늘을 제외한 7일 데이터를 모두 저장")
    void save_new_item_13days() {
        // given
        Long itemId = 6812005L; // 달인용 제작 키트

        MarketItemDetailResponse detail = new MarketItemDetailResponse();
        detail.setName("달인용 제작 키트");
        detail.setTradeRemainCount(null);
        detail.setBundleCount(1);

        List<MarketItemStatsResponse> stats = List.of(
                new MarketItemStatsResponse(LocalDate.now().minusDays(1).toString(), 100.0, 20),
                new MarketItemStatsResponse(LocalDate.now().minusDays(2).toString(), 200.0, 30)
        );
        detail.setStats(stats);

        // 각인서는 응답이 [dummy, 실제], 생활재료·재련재료는 [실제] 로 Response가 반환
        given(marketApiClient.getMarketItemDetail(itemId))
                .willReturn(List.of(detail));

        // 신규 아이템
        given(marketItemRepository.findByItemId(itemId))
                .willReturn(Optional.empty());


        // 아이템 저장 시 ID를 부여해서 반환
        given(marketItemRepository.save(any(MarketItem.class)))
                .willAnswer(inv -> {
                    MarketItem m = inv.getArgument(0);
                    m.setId(1L);
                    return m;
                });

        // when
        marketItemCollectService.fetchAndSaveItemMarketInfo(itemId);

        // then
        then(marketItemPriceHistoryRepository)
                .should(times(2))
                .save(any(MarketItemPriceHistory.class));
    }

    @Test
    @DisplayName("기존 아이템이면 어제 데이터만 저장")
    void save_existing_item_yesterday_only() {
        // given
        Long itemId = 95203905L;    // 유물 아드레날린 각인서

        MarketItem existingItem = MarketItem.builder()
                .id(1L)
                .itemId(itemId)
                .name("유물 아드레날린 각인서")
                .build();

        given(marketItemRepository.findByItemId(itemId))
                .willReturn(Optional.of(existingItem));

        LocalDate yesterday = LocalDate.now().minusDays(1);

        MarketItemStatsResponse yesterdayStat = new MarketItemStatsResponse(yesterday.toString(), 100.0, 20);

        MarketItemDetailResponse detail = new MarketItemDetailResponse();
        detail.setName("유물 아드레날린 각인서");
        detail.setStats(List.of(yesterdayStat));

        given(marketApiClient.getMarketItemDetail(itemId))
                .willReturn(List.of(detail));

        // 어제 데이터는 DB에 존재하지 않음
        given(marketItemPriceHistoryRepository.existsByMarketItem_IdAndPriceDate(existingItem.getId(), yesterday))
                .willReturn(false);

        // when
        marketItemCollectService.fetchAndSaveItemMarketInfo(itemId);

        // then
        then(marketItemPriceHistoryRepository)
                .should(times(1))
                .save(any(MarketItemPriceHistory.class));
    }
}