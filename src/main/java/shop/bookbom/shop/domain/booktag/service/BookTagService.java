package shop.bookbom.shop.domain.booktag.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.booktag.dto.response.BookTagInfoResponse;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.exception.BookTagAlreadyExistException;
import shop.bookbom.shop.domain.booktag.exception.BookTagNotFoundException;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.exception.TagNotFoundException;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class BookTagService {
    private final BookTagRepository bookTagRepository;
    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    // 책 태그 조회
    @Transactional(readOnly = true)
    public List<BookTagInfoResponse> getBookTagInformation(long bookId) {
        // 주어진 bookId에 해당하는 BookTag 목록을 가져옴
        List<BookTag> bookTags = bookTagRepository.findAllByBookId(bookId);
        // BookTagResponse 형태의 리스트 생성
        List<BookTagInfoResponse> bookTagResponses = new ArrayList<>();
        for (BookTag bookTag : bookTags) {
            BookTagInfoResponse bookTagResponse = BookTagInfoResponse.builder()
                    .tagName(bookTag.getTag().getName())
                    .build();
            // 리스트에 추가
            bookTagResponses.add(bookTagResponse);
        }
        // 리스트를 반환
        return bookTagResponses;
    }


    //책 태그 등록
    @Transactional
    public void saveTagService(long tagId, long bookId) {
        //Book 정보를 가져옴, 정보가 없을 시엔 예외 처리
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        //Tag 정보를 가져옴, 정보가 없을 시엔 예외 처리
        Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
        //이미 해당 책 태그가 존재할 경우 예외처리
        bookTagRepository.findByBookIdAndTagId(bookId, tagId)
                .ifPresent(booktag -> {
                    throw new BookTagAlreadyExistException();
                });
        //BookTag 엔티티 생성
        BookTag bookTag = BookTag.builder()
                .book(book)
                .tag(tag).build();
        //레포지토리에 접근해 저장 실행
        bookTagRepository.save(bookTag);
    }

    //책 태그 삭제
    @Transactional
    public void deleteBookTagService(long bookTagId) {
        //bookTag 검증
        bookTagRepository.findById(bookTagId).orElseThrow(BookTagNotFoundException::new);
        //레포지토리에 접근해 삭제 실행
        bookTagRepository.deleteById(bookTagId);
    }
}
