package shop.bookbom.shop.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.custom.BookRepositoryCustom;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    // 베스트 도서 목록: 제목, 작가, 출판사, 출판일자, 가격, 설명 , 별점, 리뷰갯수. pageable(별점순)

    // 베스트 도서 목록: 제목, 작가, 출판사, 출판일자, 가격, 설명 , 별점, 리뷰갯수. pageable(view 순)

    // 도서 전체 목록: 제목, 작가, 출판사, 출판일자, 가격, 설명, 별점, 리뷰갯수. pageable(가격 높/낮은순)
    // 도서 전체 목록: 제목, 작가, 출판사, 출판일자, 가격, 설명, 별점, 리뷰갯수. pageable(상품명순)
    // 도서 전체 목록: 제목, 작가, 출판사, 출판일자, 가격, 설명, 별점, 리뷰갯수. pageable(발매일순)

    // 장바구니, 주문: 표지, 제목, 가격, 할인가격, 적립율
}
