package org.example.lostarkbackend.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.markets.service.MarketItemCollectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MarketItemScheduler {

    private final MarketItemCollectService marketItemCollectService;

    // 수집 대상 itemId
    @Value("${market.item.engraving.ids}")
    private List<Long> engravingItemIds;

    @Value("${market.item.reforging.ids}")
    private List<Long> reforgingItemIds;

    @Value("${market.item.reforging.additional.ids}")
    private List<Long> additionalItemIds;

    @Value("${market.item.weapon.evolution.ids}")
    private List<Long> evolutionItemIds;

    @Value("${market.item.arkgrid.gem.ids}")
    private List<Long> arkgridGemIds;

    @Value("${market.item.battle.recovery.ids}")
    private List<Long> batttleRecoveryItemIds;

    @Value("${market.item.battle.attack.ids}")
    private List<Long> battleAttackItemIds;

    @Value("${market.item.battle.utility.ids}")
    private List<Long> battleUtilityItemIds;

    @Value("${market.item.battle.buff.ids}")
    private List<Long> batttleBuffItemIds;

    @Value("${market.item.cooking.food.ids}")
    private List<Long> cookingFoodItemIds;

    @Value("${market.item.lifeskill.gathering.ids}")
    private List<Long> lifeskillGatheringItemIds;

    @Value("${market.item.lifeskill.logging.ids}")
    private List<Long> lifeskillLoggingItemIds;

    @Value("${market.item.lifeskill.mining.ids}")
    private List<Long> lifeskillMiningItemIds;

    @Value("${market.item.lifeskill.hunting.ids}")
    private List<Long> lifeskillHuntingItemIds;

    @Value("${market.item.lifeskill.fishing.ids}")
    private List<Long> lifeskillFishingItemIds;

    @Value("${market.item.lifeskill.archaeology.ids}")
    private List<Long> lifeskillArchaeologyItemIds;

    @Value("${market.item.lifeskill.misc.ids}")
    private List<Long> lifeskillMiscItemIds;

    // 매일 오전 0시 0분에 실행
    @Scheduled(cron = "10 05 01 * * *")
    public void updateMarketPrices() {

        log.info("[Scheduler] 오늘 시세 수집 시작");

        List<Long> itemIds = new ArrayList<>();
        itemIds.addAll(engravingItemIds);
        itemIds.addAll(reforgingItemIds);
        itemIds.addAll(additionalItemIds);
        itemIds.addAll(evolutionItemIds);
        itemIds.addAll(arkgridGemIds);
        itemIds.addAll(batttleRecoveryItemIds);
        itemIds.addAll(battleAttackItemIds);
        itemIds.addAll(battleUtilityItemIds);
        itemIds.addAll(batttleBuffItemIds);
        itemIds.addAll(cookingFoodItemIds);
        itemIds.addAll(lifeskillGatheringItemIds);
        itemIds.addAll(lifeskillLoggingItemIds);
        itemIds.addAll(lifeskillMiningItemIds);
        itemIds.addAll(lifeskillHuntingItemIds);
        itemIds.addAll(lifeskillFishingItemIds);
        itemIds.addAll(lifeskillArchaeologyItemIds);
        itemIds.addAll(lifeskillMiscItemIds);

        for (Long id : itemIds) {
            marketItemCollectService.fetchAndSaveItemMarketInfo(id);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Interrupted. itemId={}", id);
                continue;
            } catch (Exception e) {
                log.error("시세 수집 실패 itemId={}", id, e);
            }
        }

        log.info("[Scheduler] 오늘 시세 수집 종료");
    }

}
