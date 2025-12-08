package org.example.lostarkbackend.domain.markets.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(
        name = "market_item_price_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_item_price_date",
                        columnNames = {"item_id", "price_date"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MarketItemPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private MarketItem marketItem;

    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    @Column(name = "avg_price", nullable = false)
    private Double avgPrice;

    @Column(name = "trade_count", nullable = false)
    private Integer tradeCount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
}
