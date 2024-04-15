package shop.bookbom.shop.domain.book.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.custom.BookRepositoryCustom;
import shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Query("SELECT new shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse(b.title, b.cost) FROM Book b WHERE b.id = :bookId")
    BookTitleAndCostResponse getTitleAndCostById(@Param("bookId") Long bookId);

    // 베스트 도서,도서 전체 목록: 표지, 제목, 작가, 출판사, 출판일자, 포인트, 가격, 할인가격, 설명 , 별점, 리뷰갯수. pageable(별점순)
//    @Query(value =
//            "SELECT new shop.bookbom.shop.domain.book.dto.response.BookMediumResponse(b,b.authors,b.tags,b.bookFiles) " +
//                    "FROM Book b " +
//                    "WHERE b.id = :bookId ORDER BY :condition")
//    Page<BookMediumResponse> getPageableBookMediumInfoByCondition(@Param("condition") String condition,
//                                                                  Pageable pageable);
//
//    @Query(value =
//            "SELECT new shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse(b,b.bookFiles) " +
//                    "FROM Book b " +
//                    "WHERE b.id = :bookId ORDER BY :condition")
//    Page<BookSimpleResponse> getPageableBookSimpleInfoByCondition(@Param("condition") String condition,
//                                                                  Pageable pageable);


}
