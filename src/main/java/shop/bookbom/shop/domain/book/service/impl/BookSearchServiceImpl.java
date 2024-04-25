package shop.bookbom.shop.domain.book.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.SearchCondition;
import shop.bookbom.shop.domain.book.dto.SortCondition;
import shop.bookbom.shop.domain.book.repository.BookSearchRepository;
import shop.bookbom.shop.domain.book.service.BookSearchService;
import shop.bookbom.shop.domain.book.service.BookService;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {
    private final BookService bookService;
    private final BookSearchRepository bookSearchRepository;


    @Override
    @Transactional(readOnly = true)
    public Page<BookSearchResponse> search(Pageable pageable, String keyword, String searchCond, String sortCond) {
        SortCondition sortCondition = SortCondition.valueOf(sortCond.toUpperCase());
        SearchCondition searchCondition = SearchCondition.valueOf(searchCond.toUpperCase());
        Page<BookDocument> result = bookSearchRepository.search(pageable, keyword, searchCondition, sortCondition);
        return result.map(this::documentToResponse);
    }

    /**
     * 검색 결과에서 작가 정보를 추출하는 메서드입니다.
     *
     * @param content 검색 결과
     * @return 작가 정보 리스트
     */
    private static List<AuthorResponse> getAuthors(BookDocument content) {
        if (content.getAuthorNames() == null) {
            return new ArrayList<>();
        }
        String[] authorNames = content.getAuthorNames().split("\\|");
        String[] authorIds = content.getAuthorIds().split("\\|");
        String[] authorRoles = content.getAuthorRoles().split("\\|");
        List<AuthorResponse> authors = new ArrayList<>();
        for (int i = 0; i < authorNames.length; i++) {
            AuthorResponse author = AuthorResponse.builder()
                    .id(Long.parseLong(authorIds[i].trim()))
                    .name(authorNames[i].trim())
                    .role(authorRoles[i].trim())
                    .build();
            authors.add(author);
        }
        return authors;
    }

    /**
     * 검색 결과를 DTO 객체로 변환하는 메서드입니다.
     *
     * @param content elasticsearch 검색 결과
     * @return 검색 결과 DTO
     */
    public BookSearchResponse documentToResponse(BookDocument content) {
        List<AuthorResponse> authors = getAuthors(content);

        return BookSearchResponse.builder()
                .id(content.getBookId())
                .thumbnail(content.getUrl())
                .title(content.getBookTitle())
                .author(authors)
                .publisherId(content.getPublisherId())
                .publisherName(content.getPublisherName())
                .pubDate(content.getPubDate())
                .price(content.getCost())
                .discountPrice(content.getDiscountCost())
                .reviewRating(bookService.getReviewRating(content.getBookId()))
                .reviewCount(bookService.getReviewCount(content.getBookId()))
                .build();
    }
}
