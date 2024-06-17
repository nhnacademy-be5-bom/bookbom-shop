package shop.bookbom.shop.domain.wish.repository;

import static shop.bookbom.shop.domain.book.entity.QBook.book;
import static shop.bookbom.shop.domain.bookfile.entity.QBookFile.bookFile;
import static shop.bookbom.shop.domain.file.entity.QFile.file;
import static shop.bookbom.shop.domain.publisher.entity.QPublisher.publisher;
import static shop.bookbom.shop.domain.wish.entity.QWish.wish;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;

public class WishCustomRepositoryImpl implements WishCustomRepository{
    private final JPAQueryFactory queryFactory;

    public WishCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<WishInfoResponse> getWishList(Long userId, Pageable pageable) {
        List<WishInfoResponse> wishList = queryFactory
                .select(
                        Projections.fields(
                                WishInfoResponse.class,
                                book.id,
                                book.title,
                                publisher.name.as("publisher"),
                                book.cost,
                                book.discountCost,
                                file.url
                        )
                )
                .from(wish)
                .innerJoin(wish.book, book)
                .innerJoin(book.publisher, publisher)
                .leftJoin(book.bookFiles, bookFile)
                .leftJoin(bookFile.file, file)
                .where(wish.member.id.eq(userId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(wish.count())
                .from(wish)
                .where(wish.member.id.eq(userId));

        return PageableExecutionUtils.getPage(wishList, pageable, countQuery::fetchOne);
    }
}
