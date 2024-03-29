package shop.bookbom.shop.deletereasoncategory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "delete_reason_category")
public class DeleteReasonCategory {

    @Id
    @Column(name = "delete_reason_category_id")
    private Long id;

    private String name;

    @Builder
    public DeleteReasonCategory(String name) {
        this.name = name;
    }
}
