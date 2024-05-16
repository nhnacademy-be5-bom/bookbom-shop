package shop.bookbom.shop.domain.book.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.book.document.BookDocument;

@Repository
public interface BookDocumentRepository extends ElasticsearchRepository<BookDocument, Long> {
}
