package shop.bookbom.shop.domain.wish.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.entity.Wish;
import shop.bookbom.shop.domain.wish.exception.WishNotFoundException;
import shop.bookbom.shop.domain.wish.repository.WishRepository;
import shop.bookbom.shop.domain.wish.service.impl.WishServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private WishServiceImpl wishService;

    @Test
    @DisplayName("찜 도서 추가")
    void testAddWish() {
        // given
        List<Long> items = new ArrayList<>();
        items.add(1L);

        Book book = mock(Book.class);
        Member member = mock(Member.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // when
        wishService.addWish(items, 1L);

        // then
        verify(wishRepository, times(1)).save(any(Wish.class));
    }

    @Test
    @DisplayName("찜 목록에서 도서 삭제")
    void testDeleteWish() {
        // given
        Long wishId = 1L;

        // existsByBookIdAndMemberId() 메서드가 true를 반환하도록 설정
        when(wishRepository.existsByBookIdAndMemberId(wishId, 1L)).thenReturn(true);

        // findByBookIdAndMemberId() 메서드가 원하는 Wish 객체를 반환하도록 설정
        when(wishRepository.findByBookIdAndMemberId(wishId, 1L)).thenReturn(mock(Wish.class));

        // when
        wishService.deleteWish(wishId, 1L);

        // then
        verify(wishRepository, times(1)).delete(any(Wish.class));
    }

//    @Test
//    @DisplayName("찜 목록 정보 조회")
//    void testGetWishInfo() {
//        // given
//        Long userId = 1L;
//        Wish wish = mock(Wish.class);
//        Book book = mock(Book.class);
//        Publisher publisher = mock(Publisher.class);
//
//        when(wish.getBook()).thenReturn(book);
//        when(book.getPublisher()).thenReturn(publisher);
//        when(publisher.getName()).thenReturn("Name");
//
//        List<Wish> wishList = List.of(wish);
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Wish> wishPage = new PageImpl<>(wishList, pageable, wishList.size());
//
//        // when
//        Page<WishInfoResponse> result = wishService.getWishInfo(userId, pageable);
//
//        // then
//        assertNotNull(result);
//        assertEquals(1, result.getTotalElements()); // 반환된 결과 리스트의 크기가 1인지 확인
//    }

    @Test
    @DisplayName("찜 목록에서 삭제 null 예외")
    void testDeleteNotExistsWish() {
        // given
        Long wishId = 2000L;

        // 존재하지 않는 wish일때
        when(wishRepository.existsByBookIdAndMemberId(eq(2000L), anyLong())).thenReturn(false);

        // when & then
        assertThrows(WishNotFoundException.class, () -> wishService.deleteWish(wishId, 1L));
    }
}
