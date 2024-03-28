package shop.bookbom.shop.wrapper.entity;

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
@Table(name = "wrapper")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Wrapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wrapper_id")
    private Long id;

    private String name;

    private int cost;

    @Builder
    public Wrapper(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
}
