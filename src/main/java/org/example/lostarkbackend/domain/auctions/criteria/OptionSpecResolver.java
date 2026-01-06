package org.example.lostarkbackend.domain.auctions.criteria;

import java.util.Arrays;

public class OptionSpecResolver {

    public static OptionSpec resolve(String optionName, double value) {

        String normalized = normalize(optionName);

        return Arrays.stream(OptionSpec.values())
                .filter(spec ->
                        spec.getOptionName().equals(normalized)
                        && Double.compare(spec.getValue(), value) == 0
                )
                .findFirst().orElse(null);  // 나중에 ETC 처리
    }

    private static String normalize(String optionName) {
        return optionName == null ? null : optionName.trim();
    }
}
