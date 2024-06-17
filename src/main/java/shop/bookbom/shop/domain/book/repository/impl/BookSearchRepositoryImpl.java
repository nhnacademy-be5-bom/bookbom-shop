package shop.bookbom.shop.domain.book.repository.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.dto.SearchCondition;
import shop.bookbom.shop.domain.book.dto.SortCondition;
import shop.bookbom.shop.domain.book.repository.BookSearchRepository;

@Repository
@RequiredArgsConstructor
public class BookSearchRepositoryImpl implements BookSearchRepository {
    private static final String NORI_FIELD = ".nori";
    private static final String NGRAM_FIELD = ".ngram";
    private final ElasticsearchOperations operations;

    /**
     * 키워드 검색 쿼리를 만들어 주는 메서드입니다.
     *
     * @param pageable   페이지 정보
     * @param keyword    검색 키워드
     * @param searchCond 검색 가중치 조건
     * @param sortCond   정렬 조건
     * @return 검색 쿼리
     */
    private static Query createQuery(
            Pageable pageable,
            String keyword,
            SearchCondition searchCond,
            SortCondition sortCond
    ) {
        float primaryBoost = 200F;
        String boostField = searchCond.getFieldName();
        return NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(
                                        buildMatchQuery(keyword, "title", boostField, primaryBoost, 100f),
                                        buildMatchQuery(keyword, "author_names", boostField, primaryBoost, 50f),
                                        buildMatchQuery(keyword, "publisher_name", boostField, primaryBoost, 40f)
                                )
                        ))
                .withSort(buildSortCondition(sortCond))
                .withPageable(pageable)
                .build();
    }

    @Override
    public Page<BookDocument> search(
            Pageable pageable,
            String keyword,
            SearchCondition searchCond,
            SortCondition sortCond
    ) {
        Query query = createQuery(pageable, keyword, searchCond, sortCond);

        SearchHits<BookDocument> searchHits = operations.search(query, BookDocument.class);
        List<BookDocument> content = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, searchHits.getTotalHits());
    }

    /**
     * 정렬 조건을 쿼리에 넣을 수 있도록 만들어주는 메서드입니다.
     *
     * @param condition 정렬 조건
     * @return Sort 객체
     */
    private static Sort buildSortCondition(SortCondition condition) {
        if (condition.getFieldName().isEmpty()) {
            return Sort.unsorted();
        }
        return Sort.by(condition.getDirection(), condition.getFieldName());
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
        return QueryBuilders.multiMatch(m -> m
                .query(keyword)
                .fields(field, field + NORI_FIELD, field + NGRAM_FIELD)
                .boost(field.equals(firstValue) ? firstBoost : defaultBoost)
        );
    }
}
