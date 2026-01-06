package org.example.lostarkbackend.domain.auctions.criteria;

import java.util.Comparator;
import java.util.List;

public class OptionCategoryResolver {

    public static OptionCategory resolve(List<OptionSpec> specs) {

        if (specs == null || specs.isEmpty()) {
            return OptionCategory.ETC;
        }

        // HIGH, MID, LOW 티어 추출
        List<String> optionTiers = specs.stream()
                .map(OptionCategoryResolver::toOptionTier)
                .filter(optiontier -> !optiontier.equals("ETC"))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();

        if(optionTiers.isEmpty()) {
            return OptionCategory.ETC;
        }

        String key = String.join("_", optionTiers);

        for (OptionCategory category : OptionCategory.values()) {
            if(category.name().equals(key)) {
                return category;
            }
        }

        return OptionCategory.ETC;
    }

    private static String toOptionTier(OptionSpec spec) {
        if(spec.name().endsWith("_HIGH")) return "HIGH";
        if(spec.name().endsWith("_MID")) return "MID";
        if(spec.name().endsWith("_LOW")) return "LOW";

        return "ETC";
    }
}
