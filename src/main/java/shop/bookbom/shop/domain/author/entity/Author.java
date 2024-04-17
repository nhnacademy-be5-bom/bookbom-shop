package shop.bookbom.shop.domain.author.entity;

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
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Builder
    public Author(String name) {
        this.name = name;
    }

    @Builder(builderMethodName = "updateBuilder")
    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
