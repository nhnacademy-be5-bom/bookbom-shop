package shop.bookbom.shop.wish.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.book.entity.Book;
import shop.bookbom.shop.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    public Long wishId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Member member;

    @Builder
    public Wish(Long bookId,
                Long userId,
                Member member,
                Book book) {
        this.bookId = bookId;
        this.userId = userId;
        this.member = member;
        this.book = book;
    }
}
