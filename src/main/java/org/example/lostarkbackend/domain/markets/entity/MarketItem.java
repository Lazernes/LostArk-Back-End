package org.example.lostarkbackend.domain.markets.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "market_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MarketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "trade_remain_count")
    private Integer tradeRemainCount;

    @Column(name = "bundle_count")
    private Integer bundleCount;

    /*
    아이템 등급은 DB에는 VARCHAR로 저장
     */
    @Enumerated(EnumType.STRING)
    private MarketItemGrade grade;

    /*
    ToolTip 원본 JSON 저장
    MySQL JSON 타입과 연결
     */
    @Column(columnDefinition = "JSON")
    private String tooltip;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    public void updateItemInfo(Integer tradeRemainCount, Integer bundleCount, String tooltip) {
//
//    }

}
