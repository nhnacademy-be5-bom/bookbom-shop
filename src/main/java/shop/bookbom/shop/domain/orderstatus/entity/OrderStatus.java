package shop.bookbom.shop.domain.orderstatus.entity;

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
@Table(name = "order_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Builder
    public OrderStatus(String name) {
        this.name = name;
    }
}
