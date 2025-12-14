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

    @Value("${market.item.cooking.omelette.ids}")
    private List<Long> cookingOmeletteItemIds;

    @Value("${market.item.cooking.skewer.ids}")
    private List<Long> cookingSkewerItemIds;

    @Value("${market.item.cooking.steak.ids}")
    private List<Long> cookingSteakItemIds;

    @Value("${market.item.cooking.food.ids}")
    private List<Long> cookingFoodItemIds;

    @Value("${market.item.cooking.special.ids}")
    private List<Long> cookingSpecialItemIds;

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
    @Scheduled(cron = "10 00 00 * * *")
    public void updateMarketPrices() {

        List<Long> itemIds = new ArrayList<>();
        itemIds.addAll(engravingItemIds);
        itemIds.addAll(reforgingItemIds);
        itemIds.addAll(evolutionItemIds);
        itemIds.addAll(arkgridGemIds);
        itemIds.addAll(batttleRecoveryItemIds);
        itemIds.addAll(battleAttackItemIds);
        itemIds.addAll(battleUtilityItemIds);
        itemIds.addAll(batttleBuffItemIds);
        itemIds.addAll(cookingOmeletteItemIds);
        itemIds.addAll(cookingSkewerItemIds);
        itemIds.addAll(cookingSteakItemIds);
        itemIds.addAll(cookingFoodItemIds);
        itemIds.addAll(cookingSpecialItemIds);
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
                break;
            }
        }
    }

}
