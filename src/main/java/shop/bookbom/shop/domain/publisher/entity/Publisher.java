package shop.bookbom.shop.domain.publisher.entity;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Builder
    public Publisher(String name) {
        this.name = name;
    }

    @Builder(builderMethodName = "updateBuilder")
    public Publisher(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
