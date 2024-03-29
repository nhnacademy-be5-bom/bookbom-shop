package shop.bookbom.shop.domain.deletereason.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import shop.bookbom.shop.domain.deletereasoncategory.entity.DeleteReasonCategory;
import shop.bookbom.shop.domain.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delete_reason")
public class DeleteReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delete_reason_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_reason_category_id", nullable = false)
    private DeleteReasonCategory deleteReasonCategory;

    @Builder
    public DeleteReason(Member member, DeleteReasonCategory deleteReasonCategory) {
        this.member = member;
        this.deleteReasonCategory = deleteReasonCategory;
    }
}
