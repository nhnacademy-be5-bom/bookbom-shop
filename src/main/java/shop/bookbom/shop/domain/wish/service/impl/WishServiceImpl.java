package shop.bookbom.shop.domain.wish.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.entity.Wish;
import shop.bookbom.shop.domain.wish.exception.WishDuplicateValueException;
import shop.bookbom.shop.domain.wish.exception.WishNotFoundException;
import shop.bookbom.shop.domain.wish.repository.WishRepository;
import shop.bookbom.shop.domain.wish.service.WishService;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishRepository wishRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * 찜 목록에 도서를 추가합니다
     *
     * @param books 추가할 도서 리스트
     * @param userId 회원 ID
     */
    @Override
    @Transactional
    public void addWish(List<Long> books, Long userId) {
        books.forEach(bookId -> {
                    Book book = bookRepository.findById(bookId)
                            .orElseThrow(BookNotFoundException::new);
                    Member member = memberRepository.findById(userId)
                            .orElseThrow(MemberNotFoundException::new);
                    Wish wish = Wish.builder()
                            .book(book)
                            .member(member)
                            .build();

                    boolean isExistWish = wishRepository.existsByBookIdAndMemberId(book.getId(), member.getId());
                    if (isExistWish) {
                        throw new WishDuplicateValueException();
                    } else {
                        wishRepository.save(wish);
                    }
                }
        );
    }

    /**
     * 찜 목록에 있는 도서를 삭제합니다
     *
     * @param wishId 삭제할 도서 wish ID
     * @param userId 회원 ID
     */
    @Override
    @Transactional
    public void deleteWish(Long wishId, Long userId) {
        boolean isExistWish = wishRepository.existsByBookIdAndMemberId(wishId, userId);
        if(!isExistWish){
            throw new WishNotFoundException();
        } else{
            Wish wish = wishRepository.findByBookIdAndMemberId(wishId, userId);
            wishRepository.delete(wish);
        }
    }

    /**
     *  회원의 찜 목록을 조회합니다.
     *
     * @param userId 회원 ID
     * @param pageable 페이징 처리
     * @return 회원의 찜 목록
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WishInfoResponse> getWishInfo(Long userId, Pageable pageable) {
        return wishRepository.getWishList(userId, pageable);
    }
}
