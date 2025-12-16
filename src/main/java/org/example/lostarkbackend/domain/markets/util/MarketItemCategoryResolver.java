package org.example.lostarkbackend.domain.markets.util;

import lombok.RequiredArgsConstructor;
import org.example.lostarkbackend.domain.markets.entity.MarketItemCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MarketItemCategoryResolver {

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

    public MarketItemCategory resolve(Long itemId) {
        if (engravingItemIds.contains(itemId)) {
            return MarketItemCategory.ENGRAVING;
        }

        if (reforgingItemIds.contains(itemId) || additionalItemIds.contains(itemId) || evolutionItemIds.contains(itemId)) {
            return MarketItemCategory.REFORGING;
        }

        if(arkgridGemIds.contains(itemId)){
            return MarketItemCategory.ARK_GRID;
        }

        if(batttleRecoveryItemIds.contains(itemId) || battleAttackItemIds.contains(itemId) || battleUtilityItemIds.contains(itemId) || batttleBuffItemIds.contains(itemId)) {
            return MarketItemCategory.BATTLE_ITEM;
        }

        if(cookingFoodItemIds.contains(itemId)) {
            return MarketItemCategory.COOKING;
        }

        if(lifeskillGatheringItemIds.contains(itemId) || lifeskillLoggingItemIds.contains(itemId) || lifeskillMiningItemIds.contains(itemId)
                || lifeskillHuntingItemIds.contains(itemId) ||  lifeskillFishingItemIds.contains(itemId)
                || lifeskillArchaeologyItemIds.contains(itemId) ||  lifeskillMiscItemIds.contains(itemId)) {
            return MarketItemCategory.LIFESKILL;
        }

        return null;
    }
}
