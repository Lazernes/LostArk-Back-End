package org.example.lostarkbackend.domain.markets.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class TooltipIconExtractor {

    private static final String CDN = "https://cdn-lostark.game.onstove.com/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String extractIconUrl(String tooltipJson) {
        if (tooltipJson == null || tooltipJson.isBlank()) {
            return null;
        }

        try {
            JsonNode root = objectMapper.readTree(tooltipJson);

            String iconPath = root
                    .path("Element_001")
                    .path("value")
                    .path("slotData")
                    .path("iconPath")
                    .asText();

            if(iconPath == null || iconPath.isBlank()) {
                return null;
            }


            return CDN + iconPath;

        } catch (Exception e) {
            return null;
        }


    }
}
