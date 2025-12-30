package org.example.lostarkbackend.domain.auctions;

public enum OptionSpec {

    // NECKLACE
    DAMAGE_TO_ENEMY_HIGH("적에게 주는 피해 증가", 2.00),
    DAMAGE_TO_ENEMY_MID("적에게 주는 피해 증가", 1.20),
    DAMAGE_TO_ENEMY_LOW("적에게 주는 피해 증가", 0.55),

    ADDITIONAL_DAMAGE_HIGH("추가 피해", 2.60),
    ADDITIONAL_DAMAGE_MID("추가 피해", 1.60),
    ADDITIONAL_DAMAGE_LOW("추가 피해", 0.80),

    STIGMA_EFFECT_HIGH("낙인력", 8.00),
    STIGMA_EFFECT_MID("낙인력", 4.80),
    STIGMA_EFFECT_LOW("낙인력", 2.15),

    GAUGE_GAIN_HIGH("세레나데, 신앙, 조화 게이지 획득량 증가", 6.00),
    GAUGE_GAIN_MID("세레나데, 신앙, 조화 게이지 획득량 증가", 3.60),
    GAUGE_GAIN_LOW("세레나데, 신앙, 조화 게이지 획득량 증가", 1.60),

    // EARRING
    ATTACK_PERCENT_HIGH("공격력 %", 1.55),
    ATTACK_PERCENT_MID("공격력 %", 0.95),
    ATTACK_PERCENT_LOW("공격력 %", 0.40),

    WEAPON_DAMAGE_PERCENT_HIGH("무기 공력력 %", 3.00),
    WEAPON_DAMAGE_PERCENT_MID("무기 공격력 %", 1.80),
    WEAPON_DAMAGE_PERCENT_LOW("무기 공격력 %", 0.80),

    SHIELD_EFFECT_HIGH("파티원 보호막 효과", 3.50),
    SHIELD_EFFECT_MID("파티원 보호막 효과", 2.10),
    SHIELD_EFFECT_LOW("파티원 보호막 효과", 0.95),

    RECOVERY_EFFECT_HIGH("파티원 회복 효과", 3.50),
    RECOVERY_EFFECT_MID("파티원 회복 효과", 2.10),
    RECOVERY_EFFECT_LOW("파티원 회복 효과", 0.95),

    // RING
    CRITICAL_RATE_HIGH("치명타 적중률", 1.55),
    CRITICAL_RATE_MID("치명타 적중률", 0.95),
    CRITICAL_RATE_LOW("치명타 적중률", 0.40),

    CRITICAL_DAMAGE_HIGH("치명타 피해", 4.00),
    CRITICAL_DAMAGE_MID("치명타 피해", 2.40),
    CRITICAL_DAMAGE_LOW("치명타 피해", 1.10),

    ATTACK_BUFF_HIGH("아군 공격력 강화 효과", 5.00),
    ATTACK_BUFF_MID("아군 공격력 강화 효과", 3.00),
    ATTACK_BUFF_LOW("아군 공격력 강화 효과", 1.35),

    DAMAGE_BUFF_HIGH("아군 피해량 강화 효과", 7.50),
    DAMAGE_BUFF_MID("아군 피해량 강화 효과", 4.50),
    DAMAGE_BUFF_LOW("아군 피해량 강화 효과", 2.00),

    // COMMON
    ATTACK_HIGH("공격력", 390),
    ATTACK_MID("공격력", 195),
    ATTACK_LOW("공격력", 80),

    WEAPON_DAMAGE_HIGH("무기 공력력", 960),
    WEAPON_DAMAGE_MID("무기 공력력", 480),
    WEAPON_DAMAGE_LOW("무기 공력력", 195),

    MAX_LIFE_HIGH("최대 생명력", 6500),
    MAX_LIFE_MID("최대 생명력", 3250),
    MAX_LIFE_LOW("최대 생명력", 1300);

    private final String optionName;
    private final double value;

    OptionSpec(String optionName, double value) {
        this.optionName = optionName;
        this.value = value;
    }
}
