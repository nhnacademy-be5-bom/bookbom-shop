package shop.bookbom.shop.domain.pointrate.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_rate")
public class PointRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_rate_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "earn_type", nullable = false)
    private EarnPointType earnType;

    @Column(name = "earn_point", nullable = false)
    private int earnPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_type", nullable = false)
    private ApplyPointType applyType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public PointRate(
            String name,
            EarnPointType earnType,
            int earnPoint,
            ApplyPointType applyType,
            LocalDateTime createdAt
    ) {
        this.name = name;
        this.earnType = earnType;
        this.earnPoint = earnPoint;
        this.applyType = applyType;
        this.createdAt = createdAt;
    }
}
