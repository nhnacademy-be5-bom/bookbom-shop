package shop.bookbom.shop.domain.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectBookResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.repository.WrapperRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    BookRepository bookRepository;
    @Mock
    BookFileRepository bookFileRepository;
    @Mock
    WrapperRepository wrapperRepository;

    @Test
    @DisplayName("주문 책에 대한 정보 얻기")
    void getOrderBookInfo() throws Exception {
        //given
        List<BeforeOrderRequest> beforeOrderRequestList = new ArrayList<>();
        beforeOrderRequestList.add(new BeforeOrderRequest(1L, 2));
        beforeOrderRequestList.add(new BeforeOrderRequest(2L, 3));
        BookTitleAndCostResponse testBook1 = BookTitleAndCostResponse.builder().title("test book1").cost(1000).build();
        when(bookRepository.getTitleAndCostById(1L)).thenReturn(
                Optional.of(BookTitleAndCostResponse.builder().title("test book1").cost(1000).build()));
        when(bookRepository.getTitleAndCostById(2L)).thenReturn(
                Optional.of(BookTitleAndCostResponse.builder().title("test book2").cost(2000).build()));
        when(bookFileRepository.getBookImageUrl(1L)).thenReturn("http://img1.jpg");
        when(bookFileRepository.getBookImageUrl(2L)).thenReturn("http://img2.jpg");

        Wrapper.builder()
                .name("포장지1").cost(100).build();
        Wrapper.builder()
                .name("포장지2").cost(200).build();
        List<Wrapper> wrapperList = new ArrayList<>();
        when(wrapperRepository.findAll()).thenReturn(wrapperList);

        //when
        BeforeOrderResponse response =
                orderService.getOrderBookInfo(new BeforeOrderRequestList(beforeOrderRequestList));

        //then
        assertEquals(2, response.getBeforeOrderBookResponseList().size());
        assertEquals(5, response.getTotalOrderCount());
        assertEquals(wrapperList, response.getWrapperList());

        BeforeOrderBookResponse bookResponse = response.getBeforeOrderBookResponseList().get(0);
        assertEquals("test book1", bookResponse.getTitle());
        assertEquals(1000, bookResponse.getCost());
        assertEquals(2, bookResponse.getQuantity());
        assertEquals("http://img1.jpg", bookResponse.getImageUrl());
    }

    @Test
    @DisplayName("주문 전 - 책이 존재하지 않을 때")
    public void beforeorder_bookNotExistException() throws Exception {
        //given
        List<BeforeOrderRequest> requestList = new ArrayList<>();
        Long bookId = 1000000L;
        requestList.add(new BeforeOrderRequest(bookId, 4));
        BeforeOrderRequestList request = new BeforeOrderRequestList(requestList);

        // getTitleAndCostById 메서드가 호출될 때 BookNotFoundException 발생하도록 설정
        when(bookRepository.getTitleAndCostById(bookId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(BookNotFoundException.class, () -> orderService.getOrderBookInfo(request));
    }

    @Test
    @DisplayName("포장지 선택 - 회원일 때")
    public void selectWrapper_memeber() throws Exception {
        //given
        WrapperSelectBookRequest bookRequest =
                new WrapperSelectBookRequest(1L, "test book", "http://img.jpg", "포장지 1", 3, 12000);
        List<WrapperSelectBookRequest> bookRequests = new ArrayList<>();
        bookRequests.add(bookRequest);
        WrapperSelectRequest wrapperSelectRequest = new WrapperSelectRequest(bookRequests, 3);

        //when
        WrapperSelectResponse response = orderService.selectWrapper(1L, wrapperSelectRequest);

        //then
        assertEquals(3, response.getTotalOrderCount());
        assertEquals(1L, response.getUserId());
        WrapperSelectBookResponse wrapperSelectBookResponse = response.getWrapperSelectResponseList().get(0);
        assertEquals(bookRequest.getBookTitle(), wrapperSelectBookResponse.getBookTitle());
        assertEquals(bookRequest.getCost(), wrapperSelectBookResponse.getCost());
        assertEquals(bookRequest.getWrapperName(), wrapperSelectBookResponse.getWrapperName());
        assertEquals(bookRequest.getBookId(), wrapperSelectBookResponse.getBookId());
    }

    @Test
    @DisplayName("포장지 선택 - 비회원일 때")
    public void selectWrapper_unregisteredMemeber() throws Exception {
        //given
        WrapperSelectBookRequest bookRequest =
                new WrapperSelectBookRequest(1L, "test book", "http://img.jpg", "포장지 1", 3, 12000);
        List<WrapperSelectBookRequest> bookRequests = new ArrayList<>();
        bookRequests.add(bookRequest);
        WrapperSelectRequest wrapperSelectRequest = new WrapperSelectRequest(bookRequests, 3);


        //when
        WrapperSelectResponse response = orderService.selectWrapper(null, wrapperSelectRequest);

        //then
        assertEquals(3, response.getTotalOrderCount());
        assertEquals(null, response.getUserId());
        WrapperSelectBookResponse wrapperSelectBookResponse = response.getWrapperSelectResponseList().get(0);
        assertEquals(bookRequest.getBookTitle(), wrapperSelectBookResponse.getBookTitle());
        assertEquals(bookRequest.getCost(), wrapperSelectBookResponse.getCost());
        assertEquals(bookRequest.getWrapperName(), wrapperSelectBookResponse.getWrapperName());

    }


}
