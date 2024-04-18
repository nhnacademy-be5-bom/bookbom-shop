package shop.bookbom.shop.domain.review.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id", nullable = false)
    private PointRate pointRate;

    public Review(
            int rate,
            String content,
            LocalDateTime createdAt,
            Member member,
            Book book,
            PointRate pointRate
    ) {
        this.rate = rate;
        this.content = content;
        this.createdAt = createdAt;
        this.member = member;
        this.book = book;
        this.pointRate = pointRate;
    }
}
