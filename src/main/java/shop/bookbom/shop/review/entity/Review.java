package shop.bookbom.shop.review.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.book.entity.Book;
import shop.bookbom.shop.member.entity.Member;
import shop.bookbom.shop.pointrate.entity.PointRate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_rate_id")
    private PointRate pointRate;

    public Review(int rate,
                  String content,
                  LocalDateTime createdAt,
                  Member member,
                  Book book,
                  PointRate pointRate) {
        this.rate = rate;
        this.content = content;
        this.createdAt = createdAt;
        this.member = member;
        this.book = book;
        this.pointRate = pointRate;
    }
}
