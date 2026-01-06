package org.example.lostarkbackend.domain.auctions.criteria;

import java.util.List;

public enum AccessoryStatRange {

    NECKLACE(
            AccessoryType.NECKLACE,
            List.of(
                    new Range(15178, 15847, StatBucket.LOW),
                    new Range(15848, 16517, StatBucket.MID),
                    new Range(16518, 17187, StatBucket.HIGH),
                    new Range(17188, 17857, StatBucket.MAX)
            )
    ),

    EARRING(
            AccessoryType.EARRING,
            List.of(
                    new Range(11806, 12326, StatBucket.LOW),
                    new Range(12327, 12847, StatBucket.MID),
                    new Range(12848, 13368, StatBucket.HIGH),
                    new Range(13369, 13889, StatBucket.MAX)
            )
    ),

    RING(
            AccessoryType.RING,
            List.of(
                    new Range(10962, 11446, StatBucket.LOW),
                    new Range(11447, 11930, StatBucket.MID),
                    new Range(11931, 12414, StatBucket.HIGH),
                    new Range(12415, 12897, StatBucket.MAX)
            )
    );

    private final AccessoryType type;
    private final List<Range> ranges;

    AccessoryStatRange(AccessoryType type, List<Range> ranges) {
        this.type = type;
        this.ranges = ranges;
    }

    public static StatBucket resolve(AccessoryType type, int statValue) {
        for (AccessoryStatRange range : values()) {
            if (range.type == type) {
                return range.ranges.stream()
                        .filter(r -> r.contains(statValue))
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalArgumentException("Invalid stat value: " + statValue)
                        )
                        .bucket;
            }
        }
        throw new IllegalArgumentException("Unsupported accessory type: " + type);
    }

    private record Range(int min, int max, StatBucket bucket) {
        boolean contains(int value) {
            return value >= min && value <= max;
        }
    }
}
