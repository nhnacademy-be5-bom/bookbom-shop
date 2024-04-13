package shop.bookbom.shop.domain.book.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.document.BookDocument;
import shop.bookbom.shop.domain.book.service.BookSearchService;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {
    private final ElasticsearchOperations operations;

    @Override
    @Transactional(readOnly = true)
    public Page<BookDocument> search(Pageable pageable, String keyword) {
        String queryStr = "{\n" +
                "  \"multi_match\": {\n" +
                "    \"query\": \"" + keyword + "\",\n" +
                "    \"fields\": [\"book_title^100\", \"author_names^80\", \"description^60\", \"book_index^50\", \"publisher_name^30\"]\n" +
                "  }\n" +
                "}";

        StringQuery query = new StringQuery(queryStr);
        query.setPageable(pageable);

        SearchHits<BookDocument> searchHits = operations.search(query, BookDocument.class);
        List<BookDocument> content = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(content, pageable, totalHits);
    }
}
