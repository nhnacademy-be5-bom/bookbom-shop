package shop.bookbom.shop.domain.bookfile.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BookFileRepositoryCustom {
    String getBookImageUrl(Long bookId);
}
