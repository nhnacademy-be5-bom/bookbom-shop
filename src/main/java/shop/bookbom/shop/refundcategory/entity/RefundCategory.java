package shop.bookbom.shop.refundcategory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "return_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefundCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_category_id")
    private Long id;

    private String name;

    @Builder
    public RefundCategory(String name) {
        this.name = name;
    }
}
