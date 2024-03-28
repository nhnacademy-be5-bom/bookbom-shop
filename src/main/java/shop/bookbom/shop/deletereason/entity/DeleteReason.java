package shop.bookbom.shop.deletereason.entity;

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
import shop.bookbom.shop.deletereasoncategory.entity.DeleteReasonCategory;
import shop.bookbom.shop.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "delete_reason")
public class DeleteReason {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delete_reason_id")
    private Long deleteReasonId;

    @Column(name = "delete_reason_category_id")
    private Long deleteReasonCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_reason_category_id", insertable = false, updatable = false)
    private DeleteReasonCategory deleteReasonCategory;

    @Builder
    public DeleteReason(Long deleteReasonCategoryId,
                        Member member,
                        DeleteReasonCategory deleteReasonCategory) {
        this.deleteReasonCategoryId = deleteReasonCategoryId;
        this.member = member;
        this.deleteReasonCategory = deleteReasonCategory;
    }
}
