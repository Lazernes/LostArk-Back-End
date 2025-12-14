package org.example.lostarkbackend.domain.markets.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.lostarkbackend.domain.markets.entity.MarketItemGrade;
import org.springframework.stereotype.Component;

@Component
public class TooltipGradeExtractor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MarketItemGrade extract(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            int iconGrade = root
                    .path("Element_001")
                    .path("value")
                    .path("slotData")
                    .path("iconGrade")
                    .asInt(-1);

            if (iconGrade < 0 || iconGrade > 7) {
                return null;
            }

            return MarketItemGrade.values()[iconGrade];

        } catch (Exception e) {
            return null;
        }
    }
}
