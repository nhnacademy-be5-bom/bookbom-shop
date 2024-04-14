package shop.bookbom.shop.domain.book.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.author.dto.AuthorResponse;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.service.BookSearchService;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {
    private final ElasticsearchOperations operations;
    private final ReviewRepository reviewRepository;

    /**
     * 키워드 검색 쿼리를 만들어 주는 메서드입니다.
     *
     * @param pageable   페이지 정보
     * @param keyword    검색 키워드
     * @param firstValue 우선 검색 키워드
     * @return 검색 쿼리
     */
    private static Query createQuery(
            Pageable pageable,
            String keyword,
            String firstValue
    ) {
        float firstBoost = 200F;
        return NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(
                                        buildMatchQuery(keyword, "book_title", firstValue, firstBoost, 100f),
                                        buildMatchQuery(keyword, "book_description", firstValue, firstBoost, 80f),
                                        buildMatchQuery(keyword, "author_names", firstValue, firstBoost, 60f),
                                        buildMatchQuery(keyword, "book_index", firstValue, firstBoost, 40f),
                                        buildMatchQuery(keyword, "publisher_name", firstValue, firstBoost, 30f)
                                )
                        ))
                .withPageable(pageable)
                .build();
    }

    /**
     * 필드에 해당 키워드가 일치하는 결과가 있는지 검색하는 쿼리를 만들어주는 메서드입니다.
     *
     * @param keyword      검색 키워드
     * @param field        검색하는 필드
     * @param firstValue   우선 검색 키워드
     * @param firstBoost   우선 검색 키워드 가중치
     * @param defaultBoost 해당 필드의 기본 가중치
     * @return 검색 쿼리
     */
    private static co.elastic.clients.elasticsearch._types.query_dsl.Query buildMatchQuery(
            String keyword,
            String field,
            String firstValue,
            float firstBoost,
            float defaultBoost
    ) {
        return QueryBuilders.match(m -> m
                .query(keyword)
                .field(field)
                .boost(field.equals(firstValue) ? firstBoost : defaultBoost)
        );
    }

    /**
     * 검색 결과에서 작가 정보를 추출하는 메서드입니다.
     *
     * @param content 검색 결과
     * @return 작가 정보 리스트
     */
    private static List<AuthorResponse> getAuthors(BookDocument content) {
        String[] authorNames = content.getAuthorNames().split(",");
        String[] authorIds = content.getAuthorIds().split(",");
        String[] authorRoles = content.getAuthorRoles().split(",");
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

    @Override
    @Transactional(readOnly = true)
    public Page<BookSearchResponse> search(Pageable pageable, String keyword, String firstValue) {
        Query query = createQuery(pageable, keyword, firstValue);

        SearchHits<BookDocument> searchHits = operations.search(query, BookDocument.class);
        List<BookSearchResponse> content = searchHits.stream()
                .map(s -> documentToResponse(s.getContent()))
                .collect(Collectors.toList());
        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(content, pageable, totalHits);
    }

    @Override
    @Transactional(readOnly = true)
    public long getReviewCount(Long bookId) {
        return reviewRepository.countByBookId(bookId)
                .orElse(0L);
    }

    @Override
    @Transactional(readOnly = true)
    public double getReviewRating(Long bookId) {
        return reviewRepository.avgRateByBookId(bookId)
                .orElse(0.0);
    }

    /**
     * 검색 결과를 DTO 객체로 변환하는 메서드입니다.
     *
     * @param content elasticsearch 검색 결과
     * @return 검색 결과 DTO
     */
    private BookSearchResponse documentToResponse(BookDocument content) {
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
                .reviewRating(getReviewRating(content.getBookId()))
                .reviewCount(getReviewCount(content.getBookId()))
                .build();
    }
}
