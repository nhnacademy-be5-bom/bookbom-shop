package shop.bookbom.shop.domain.wish.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.wish.dto.request.WishAddDeleteRequest;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.dto.response.WishTotalCountResponse;
import shop.bookbom.shop.domain.wish.entity.Wish;
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
     *
     * 찜 목록에 도서를 추가합니다
     *
     * @param items
     * @param userId
     */
    @Override
    @Transactional
    public void addWish(List<WishAddDeleteRequest> items, Long userId) {
        items.forEach(item -> {
                    Book book = bookRepository.findById(item.getBookId())
                            .orElseThrow(BookNotFoundException::new);
                    Member member = memberRepository.findById(userId)
                            .orElseThrow(MemberNotFoundException::new);
                    Wish wish = Wish.builder()
                            .book(book)
                            .member(member)
                            .build();

                    wishRepository.save(wish);
                }
        );
    }

    /**
     *
     * 찜 목록에 있는 도서를 삭제합니다
     *
     * @param items
     * @param userId
     */
    @Override
    @Transactional
    public void deleteWish(List<WishAddDeleteRequest> items, Long userId) {
        items.forEach(item -> {
                    Wish wish = wishRepository.findByBookIdAndMemerId(item.getBookId(), userId);
                    wishRepository.delete(wish);
                }
        );
    }

    /**
     *
     * 회원의 찜 목록을 조회합니다
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishInfoResponse> getWishInfo(Long userId) {
        List<Wish> wishBookList = wishRepository.getBookIdByMemberId(userId);
        List<WishInfoResponse> wishInfoResponseList = new ArrayList<>();
        for (Wish wish : wishBookList) {
            Book book = wish.getBook();
            if(book != null){
                wishInfoResponseList.add(new WishInfoResponse(
                        book.getTitle(),
                        book.getPublisher().getName(),
                        book.getCost(),
                        book.getDiscountCost()
                ));
            }
        }
        return wishInfoResponseList;
    }

    /**
     *
     * 회원의 모든 찜 개수를 조회합니다
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public WishTotalCountResponse getWishTotalCount(Long userId) {
        Long count = wishRepository.countByMemberId(userId);
        return new WishTotalCountResponse(count);
    }
}
