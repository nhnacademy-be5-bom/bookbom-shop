package shop.bookbom.shop.pointrate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.entity.pointrate.EarnPointType;
import shop.bookbom.shop.entity.pointrate.ApplyPointType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "point_rate")
public class PointRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_rate_id")
    private Long pointRateId;

    private String name;

    @Column(name = "earn_type")
    private EarnPointType earnType;

    @Column(name = "earn_point")
    private int earnPoint;

    @Column(name = "apply_type")
    private ApplyPointType applyType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public PointRate(String name,
                     EarnPointType earnType,
                     int earnPoint,
                     ApplyPointType applyType,
                     LocalDateTime createdAt) {
        this.name = name;
        this.earnType = earnType;
        this.earnPoint = earnPoint;
        this.applyType = applyType;
        this.createdAt = createdAt;
    }
}
