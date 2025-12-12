package org.example.lostarkbackend.domain.markets.service;

import org.example.lostarkbackend.domain.markets.dao.MarketItemPriceHistoryRepository;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemStatsResponse;
import org.example.lostarkbackend.domain.markets.entity.MarketItem;
import org.example.lostarkbackend.domain.markets.entity.MarketItemPriceHistory;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MarketItemQueryServiceTest {

    @Autowired
    private MarketItemQueryService marketItemQueryService;

    @Autowired
    private MarketItemRepository marketItemRepository;

    @Autowired
    private MarketItemPriceHistoryRepository marketItemPriceHistoryRepository;

    private MarketItem item;

    @BeforeEach
    void setUp() {
        // 외부 ID = 652039050
        item = marketItemRepository.save(
                MarketItem.builder()
                        .itemId(652039050L)
                        .name("테스트 아이템")
                        .build()
        );

        marketItemPriceHistoryRepository.save(
                MarketItemPriceHistory.builder()
                        .marketItem(item)
                        .priceDate(LocalDate.of(2025, 12, 1))
                        .avgPrice(1000.0)
                        .tradeCount(10)
                        .build()
        );

        marketItemPriceHistoryRepository.save(
                MarketItemPriceHistory.builder()
                        .marketItem(item)
                        .priceDate(LocalDate.of(2025, 12, 2))
                        .avgPrice(1200.0)
                        .tradeCount(12)
                        .build()
        );
    }

    @Test
    void 정상_아이템_기간별_조회() {
        List<MarketItemStatsResponse> result =
                marketItemQueryService.getPriceHistory(
                        652039050L,
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 2)
                );

        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.get(0).getAvgPrice()).isEqualTo(1000.0);
        Assertions.assertThat(result.get(1).getAvgPrice()).isEqualTo(1200.0);
    }

    @Test
    void 날짜_범위로_부분_조회() {
        List<MarketItemStatsResponse> result =
                marketItemQueryService.getPriceHistory(
                        652039050L,
                        LocalDate.of(2025, 12, 2),
                        LocalDate.of(2025, 12, 2)
                );

        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0).getDate()).isEqualTo("2025-12-02");
    }

    @Test
    void 데이터_없는_날짜_조회시_빈리스트_반환() {
        List<MarketItemStatsResponse> result =
                marketItemQueryService.getPriceHistory(
                        652039050L,
                        LocalDate.of(2025, 11, 1),
                        LocalDate.of(2025, 11, 5)
                );

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void 존재하지_않는_아이템ID_조회시_예외발생() {
        assertThatThrownBy(() ->
                marketItemQueryService.getPriceHistory(
                        99999999L,
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 2)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

}