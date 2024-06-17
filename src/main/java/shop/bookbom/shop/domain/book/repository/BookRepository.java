package shop.bookbom.shop.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.custom.BookRepositoryCustom;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Query("SELECT b.stock from Book b where b.id = :bookId")
    Integer getStockById(@Param("bookId") Long bookId);

    /** 최대크기 DTO 단건조회
     BookDetailResponse getBookDetailInfoById(Long bookId);
     * 중간크기 DTO 단건조회
     BookMediumResponse getBookMediumInfoById(Long bookId);
     * 최소크기 DTO 단건조회
     BookSimpleResponse getBookSimpleInfoById(Long bookId);
     * pageable: 베스트 페이지
     Page<BookMediumResponse> getPageableAndOrderByViewCountListBookMediumInfos(Pageable pageable);
     * pageable: 전체책/관리자페이지
     Page<BookMediumResponse> getPageableListBookMediumInfos(Pageable pageable);
     * pageable: 카테고리별 책 조회
     Page<BookMediumResponse> getPageableBookMediumInfosByCategoryId(Long categoryId, Pageable pageable);
     */
}
