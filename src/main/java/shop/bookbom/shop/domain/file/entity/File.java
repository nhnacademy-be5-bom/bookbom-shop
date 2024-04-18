package shop.bookbom.shop.domain.file.entity;

import java.time.LocalDateTime;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String url;

    @Column(nullable = false, length = 10)
    private String extension;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public File(String url, String extension, LocalDateTime createdAt) {
        this.url = url;
        this.extension = extension;
        this.createdAt = createdAt;
    }
}
