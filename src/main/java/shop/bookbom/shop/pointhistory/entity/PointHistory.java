package shop.bookbom.shop.pointhistory.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

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
