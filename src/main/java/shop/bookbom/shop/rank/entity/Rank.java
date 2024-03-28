package shop.bookbom.shop.rank.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.pointrate.entity.PointRate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "rank")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    private String name;

    @Column(name = "point_rate_id")
    private Long pointRateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id", insertable = false, updatable = false)
    private PointRate pointRate;

    @Builder
    public Rank(String name, Long pointRateId, PointRate pointRate) {
        this.name = name;
        this.pointRateId = pointRateId;
        this.pointRate = pointRate;
    }
}
