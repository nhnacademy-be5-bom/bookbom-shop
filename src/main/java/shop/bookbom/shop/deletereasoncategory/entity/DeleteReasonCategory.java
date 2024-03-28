package shop.bookbom.shop.deletereasoncategory.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "delete_reason_category")
public class DeleteReasonCategory {

    @Id
    @Column(name = "delete_reason_category_id")
    private Long deletedReasonCategoryId;

    private String name;
}
