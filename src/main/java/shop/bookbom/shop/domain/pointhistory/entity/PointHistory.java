package shop.bookbom.shop.domain.pointhistory.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", nullable = false)
    private Long id;

    @Column(name = "change_point", nullable = false)
    private int changePoint;

    @Column(name = "change_reason", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ChangeReason changeReason;

    @Column(name = "detail", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private PointHistoryDetail detail;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @Builder
    public PointHistory(
            int changePoint,
            ChangeReason changeReason,
            LocalDateTime changeDate,
            PointHistoryDetail detail,
            Member member
    ) {
        this.changePoint = changePoint;
        this.changeReason = changeReason;
        this.changeDate = changeDate;
        this.detail = detail;
        this.member = member;
    }
}
