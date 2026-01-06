package org.example.lostarkbackend.domain.auctions.criteria;

public enum QualityGroup {
    Q67_79(67, 79),
    Q80_89(80, 89),
    Q90_99(90, 99),
    Q100(100, 100);

    private final int min;
    private final int max;

    QualityGroup(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static QualityGroup resolve(int quality) {
        for(QualityGroup group : values()) {
            if(quality >= group.min && quality <= group.max) {
                return group;
            }
        }

        throw new IllegalArgumentException("Invalid quality" + quality);
    }
}
