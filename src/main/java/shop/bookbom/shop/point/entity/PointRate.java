package shop.bookbom.shop.point.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_rate")
public class PointRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_rate_id")
    private Long pointRateId;

    private String name;

    @Column(name = "earn_type")
    private EarnType earnType;

    @Column(name = "earn_point")
    private Integer earnPoint;

    @Column(name = "apply_type")
    private ApplyType applyType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public PointRate(String name, EarnType earnType, Integer earnPoint, ApplyType applyType,
                     LocalDateTime createdAt) {
        this.name = name;
        this.earnType = earnType;
        this.earnPoint = earnPoint;
        this.applyType = applyType;
        this.createdAt = createdAt;
    }
}