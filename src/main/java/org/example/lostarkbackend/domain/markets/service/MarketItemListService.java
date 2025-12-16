package org.example.lostarkbackend.domain.markets.service;

import lombok.RequiredArgsConstructor;
import org.example.lostarkbackend.domain.markets.dao.MarketItemRepository;
import org.example.lostarkbackend.domain.markets.dto.MarketItemPriceSummary;
import org.example.lostarkbackend.domain.markets.dto.MarketItemSummaryResponse;
import org.example.lostarkbackend.domain.markets.entity.MarketItem;
import org.example.lostarkbackend.domain.markets.util.MarketItemCategoryResolver;
import org.example.lostarkbackend.domain.markets.util.TooltipGradeExtractor;
import org.example.lostarkbackend.domain.markets.util.TooltipIconExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketItemListService {

    private final MarketItemRepository marketItemRepository;
    private final MarketItemQueryService marketItemQueryService;
    private final TooltipGradeExtractor tooltipGradeExtractor;
    private final MarketItemCategoryResolver  marketItemCategoryResolver;
    private final TooltipIconExtractor tooltipIconExtractor;

    @Transactional
    public List<MarketItemSummaryResponse> getMarketItemList() {
        List<MarketItem> items = marketItemRepository.findAll();

        return items.stream()
                .map(item -> {
                    MarketItemPriceSummary price = marketItemQueryService.calculatePriceChange(item.getId());

                    return new MarketItemSummaryResponse(
                            item.getItemId(),
                            item.getName(),
                            item.getGrade().name(),
                            item.getBundleCount(),
                            tooltipIconExtractor.extractIconUrl(item.getTooltip()),
                            marketItemCategoryResolver.resolve(item.getItemId()).name(),
                            price.getCurrentPrice(),
                            price.getPriceChange(),
                            price.getChangeRate()
                    );
                })
                .toList();
    }
}
