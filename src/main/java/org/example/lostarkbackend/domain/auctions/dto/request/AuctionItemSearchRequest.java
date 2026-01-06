package org.example.lostarkbackend.domain.auctions.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AuctionItemSearchRequest {

    private Integer ItemLevelMin;
    private Integer ItemLevelMax;
    private Integer ItemGradeQuality;
    private Integer ItemUpgradeLevel;
    private Integer ItemTradeAllowCount;

    private List<SkillOption> SkillOptions;
    private List<EtcOption> EtcOptions;

    private AuctionSortType Sort;
    private AuctionCategoryCode CategoryCode;
    private String CharacterClass;
    private Integer ItemTier;
    private String ItemGrade;
    private String ItemName;

    private Integer PageNo;
    private String SortCondition;

    @Getter
    @Builder
    public static class SkillOption {
        private Integer FirstOption;
        private Integer SecondOption;
        private Integer MinValue;
        private Integer MaxValue;
    }

    @Getter
    @Builder
    public static class EtcOption {
        private Integer FirstOption;    // 연마 효과: 7
        private Integer SecondOption;   // 추가 피해: 41
        private Integer MinValue;   // 1.20% >> 120
        private Integer MaxValue;
    }
}
