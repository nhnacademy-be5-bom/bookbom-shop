package shop.bookbom.shop.deletereason.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.deletereasoncategory.entity.DeleteReasonCategory;
import shop.bookbom.shop.member.entity.Member;

import javax.persistence.*;

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

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_reason_category_id", insertable = false, updatable = false)
    private DeleteReasonCategory deleteReasonCategory;

    @Builder
    public DeleteReason(Long deleteReasonCategoryId,
                        Long userId,
                        Member member,
                        DeleteReasonCategory deleteReasonCategory) {
        this.deleteReasonCategoryId = deleteReasonCategoryId;
        this.userId = userId;
        this.member = member;
        this.deleteReasonCategory = deleteReasonCategory;
    }
}
