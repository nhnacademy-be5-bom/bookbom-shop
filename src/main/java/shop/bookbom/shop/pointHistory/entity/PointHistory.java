package shop.bookbom.shop.pointHistory.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_history")
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id")
    private Long pointHistoryId;

    @Column(name = "change_point")
    private Integer changePoint;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @Builder
    public PointHistory(Integer changePoint, String changeReason, LocalDateTime changeDate,
                        Member member) {
        this.changePoint = changePoint;
        this.changeReason = changeReason;
        this.changeDate = changeDate;
        this.member = member;
    }
}
