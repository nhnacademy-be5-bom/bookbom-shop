package shop.bookbom.shop.domain.booktag.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.booktag.dto.BookTagResponse;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class BookTagService {
    private final BookTagRepository bookTagRepository;
    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    // 책 태그 조회
    public List<BookTagResponse> getBookTagInformation(long bookId) {
        // 주어진 bookId에 해당하는 BookTag 목록을 가져옴
        List<BookTag> bookTags = bookTagRepository.findAllByBookId(bookId);
        // BookTagResponse 형태의 리스트 생성
        List<BookTagResponse> bookTagResponses = new ArrayList<>();
        for (BookTag bookTag : bookTags) {
            BookTagResponse bookTagResponse = new BookTagResponse();
            bookTagResponse.setTagName(bookTag.getTag().getName());
            // 리스트에 추가
            bookTagResponses.add(bookTagResponse);
        }
        // 리스트를 반환
        return bookTagResponses;
    }


    //책 태그 등록
    public void saveTagService(long tagId, long bookId) {
        //책 정보를 가져옴
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        //태그 정보를 가져옴
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        //두 개의 정보가 모두 존재할때 진행
        if(optionalBook.isPresent() && optionalTag.isPresent()){
            //optional에서 Book 정보를 가져옴
            Book book = optionalBook.get();
            //optional에서 Tag 정보를 가져옴
            Tag tag = optionalTag.get();
            //BookTag 엔티티 생성
            BookTag bookTag = new BookTag(book, tag);
            //레포지토리에 접근해 저장 실행
            bookTagRepository.save(bookTag);
        }
    }

    //책 태그 삭제
    public void deleteBookTagService(long bookTagId) {
        //레포지토리에 접근해 삭제 실행
        bookTagRepository.deleteById(bookTagId);
    }
}
