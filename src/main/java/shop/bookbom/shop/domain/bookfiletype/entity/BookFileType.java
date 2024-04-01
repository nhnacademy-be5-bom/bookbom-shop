package shop.bookbom.shop.domain.bookfiletype.entity;

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
@Table(name = "book_file_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookFileType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_file_type_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Builder
    public BookFileType(String name) {
        this.name = name;
    }
}
