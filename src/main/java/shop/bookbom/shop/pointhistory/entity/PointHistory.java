package shop.bookbom.shop.pointhistory.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "point_history")
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column(name = "change_point")
    private int changePoint;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    @Builder
    public PointHistory(int changePoint,
                        String changeReason,
                        LocalDateTime changeDate,
                        Long userId,
                        Member member) {
        this.changePoint = changePoint;
        this.changeReason = changeReason;
        this.changeDate = changeDate;
        this.userId = userId;
        this.member = member;
    }
}
