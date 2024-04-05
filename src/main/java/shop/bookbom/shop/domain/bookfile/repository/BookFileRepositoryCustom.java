package shop.bookbom.shop.domain.bookfile.repository;

public interface BookFileRepositoryCustom {
    /**
     * 책 아이디로 그 책의 이미지를 불러오는 메소드
     *
     * @param bookId
     * @return url값
     */
    String getBookImageUrl(Long bookId);
}
