package shop.bookbom.shop.domain.wish.service;


import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.wish.dto.request.WishAddRequest;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.dto.response.WishTotalCountResponse;
import shop.bookbom.shop.domain.wish.entity.Wish;
import shop.bookbom.shop.domain.wish.exception.WishNotFoundException;
import shop.bookbom.shop.domain.wish.repository.WishRepository;
import shop.bookbom.shop.domain.wish.service.impl.WishServiceImpl;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        List<WishAddRequest> items = new ArrayList<>();
        WishAddRequest wishAddRequest = new WishAddRequest();
        wishAddRequest.setBookId(1L);
        items.add(wishAddRequest);

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
        Wish wish = mock(Wish.class);

        when(wishRepository.findById(anyLong())).thenReturn(Optional.of(wish));

        // when
        wishService.deleteWish(1L);

        // then
        verify(wishRepository, times(1)).delete(any(Wish.class));
    }

    @Test
    @DisplayName("찜 목록 정보 조회")
    void testGetWishInfo() {
        // given
        Long userId = 1L;
        List<Wish> wishList = new ArrayList<>();
        Wish wish = mock(Wish.class);
        Book book = mock(Book.class);
        when(wish.getBook()).thenReturn(book);
        Publisher publisher = mock(Publisher.class);
        when(book.getPublisher()).thenReturn(publisher);
        when(publisher.getName()).thenReturn("Name");
        wishList.add(wish);

        // Mockito를 사용하여 Repository가 특정 userId에 대한 Wish 리스트를 반환하도록 설정
        when(wishRepository.getBookIdByMemberId(userId)).thenReturn(wishList);

        // when
        List<WishInfoResponse> result = wishService.getWishInfo(userId);

        // then
        assertNotNull(result);
        assertEquals(1, result.size()); // 반환된 결과 리스트의 크기가 1인지 확인
    }

    @Test
    @DisplayName("찜 전체 개수 조회")
    void testGetWishTotalCount() {
        // given
        Long userId = 1L;
        long expectedCount = 10L; // 예상되는 Wish 개수

        // Repository가 특정 userId에 대한 Wish 개수를 반환하도록 설정
        when(wishRepository.countByMemberId(userId)).thenReturn(expectedCount);

        // when
        WishTotalCountResponse result = wishService.getWishTotalCount(userId);

        // then
        assertNotNull(result);
        assertEquals(expectedCount, result.getWishTotalCount()); // 반환된 결과가 예상되는 값과 일치하는지 확인
    }

    @Test
    @DisplayName("찜 목록에서 삭제 null 예외")
    void testDeleteNotExistsWish() throws Exception {
        //given
        when(wishRepository.findById(any())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> wishService.deleteWish(1L))
                .isInstanceOf(WishNotFoundException.class);
    }
}
