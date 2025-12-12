package org.example.lostarkbackend.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.lostarkbackend.domain.markets.service.MarketItemCollectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MarketItemScheduler {

    private final MarketItemCollectService marketItemCollectService;

    // 수집 대상 itemId
    @Value("${market.item.engraving.ids}")
    private List<Long> engravingItemIds;

    @Value("${market.item.reforging.ids}")
    private List<Long> reforgingItemIds;

    // 매일 오전 0시 0분에 실행
    @Scheduled(cron = "10 50 02 * * *")
    public void updateMarketPrices() {

        List<Long> itemIds = new ArrayList<>();
        itemIds.addAll(engravingItemIds);
        itemIds.addAll(reforgingItemIds);

        for (Long id : itemIds) {
            marketItemCollectService.fetchAndSaveItemMarketInfo(id);
        }
    }

}
