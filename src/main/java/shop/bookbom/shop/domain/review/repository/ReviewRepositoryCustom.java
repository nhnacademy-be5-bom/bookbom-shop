package shop.bookbom.shop.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.review.dto.response.ReviewResponse;

public interface ReviewRepositoryCustom {

    Page<ReviewResponse> getAllReviewsByBook(Book book, Pageable pageable);
}
