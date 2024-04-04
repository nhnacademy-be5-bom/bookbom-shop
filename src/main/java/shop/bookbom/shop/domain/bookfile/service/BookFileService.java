package shop.bookbom.shop.domain.bookfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;

@Service
@RequiredArgsConstructor
public class BookFileService {
    private final BookFileRepository bookFileRepository;

    //bookId로 책 이미지 url 가져오기
    public String find_bookImgUrl(Long bookId) {
        return bookFileRepository.getBookImageUrl(bookId);
    }
}
