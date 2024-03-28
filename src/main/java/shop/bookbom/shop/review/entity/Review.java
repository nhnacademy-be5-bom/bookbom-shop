package shop.bookbom.shop.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.book.entity.Book;
import shop.bookbom.shop.pointrate.entity.PointRate;
import shop.bookbom.shop.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    private int rate;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "point_rate_id")
    private Long pointRateId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id", insertable = false, updatable = false)
    private PointRate pointRate;

    public Review(int rate,
                  String content,
                  LocalDateTime createdAt,
                  Long bookId,
                  Long pointRateId,
                  Long userId,
                  Member member,
                  Book book,
                  PointRate pointRate) {
        this.rate = rate;
        this.content = content;
        this.createdAt = createdAt;
        this.bookId = bookId;
        this.pointRateId = pointRateId;
        this.userId = userId;
        this.member = member;
        this.book = book;
        this.pointRate = pointRate;
    }
}
