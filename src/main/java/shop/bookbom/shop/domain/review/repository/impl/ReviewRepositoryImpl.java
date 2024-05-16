package shop.bookbom.shop.domain.review.repository.impl;

import static shop.bookbom.shop.domain.member.entity.QMember.member;
import static shop.bookbom.shop.domain.review.entity.QReview.review;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.review.dto.response.ReviewResponse;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.review.repository.ReviewRepositoryCustom;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> getAllReviewsByBook(Book book, Pageable pageable) {
        List<Review> result = queryFactory.select(review)
                .from(review)
                .join(review.member, member).fetchJoin()
                .where(review.book.eq(book))
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ReviewResponse> content = result.stream()
                .map(ReviewResponse::from)
                .collect(Collectors.toList());


        JPAQuery<Long> countQuery = queryFactory.select(review.count())
                .from(review)
                .join(review.member, member)
                .where(review.book.eq(book));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
