package shop.bookbom.shop.domain.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OpenWrapperSelectResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectBookResponse;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
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
        when(bookRepository.findById(1L)).thenReturn(
                Optional.ofNullable(Book.builder().title("testbook")
                        .description("설명")
                        .index("목차")
                        .pubDate(LocalDate.parse("2024-05-03"))
                        .isbn10("0123456789")
                        .isbn13("0123456789123")
                        .cost(25600)
                        .discountCost(25000)
                        .packagable(true)
                        .views(210L)
                        .status(BookStatus.FOR_SALE)
                        .stock(10)
                        .publisher(Publisher.builder().name("행복출판").build())
                        .pointRate(PointRate.builder().name("적립").earnType(EarnPointType.COST).earnPoint(300).createdAt(
                                LocalDateTime.now()).applyType(ApplyPointType.BOOK).build())
                        .build()));
        when(bookFileRepository.getBookImageUrl(1L)).thenReturn("http://img1.jpg");

        Wrapper.builder()
                .name("포장지1").cost(100).build();
        Wrapper.builder()
                .name("포장지2").cost(200).build();
        List<Wrapper> wrapperList = new ArrayList<>();
        when(wrapperRepository.findAll()).thenReturn(wrapperList);
        when(bookRepository.getStockById(1L)).thenReturn(10);

        //when
        BeforeOrderResponse response =
                orderService.getOrderBookInfo(new BeforeOrderRequestList(beforeOrderRequestList));

        //then
        assertEquals(1, response.getBeforeOrderBookResponseList().size());
        assertEquals(2, response.getTotalOrderCount());
        assertEquals(wrapperList, response.getWrapperList());

        BeforeOrderBookResponse bookResponse = response.getBeforeOrderBookResponseList().get(0);
        assertEquals("testbook", bookResponse.getTitle());
        assertEquals(25600, bookResponse.getCost());
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
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(BookNotFoundException.class, () -> orderService.getOrderBookInfo(request));
    }

    @Test
    @DisplayName("포장지 선택 - 비회원일 때")
    public void selectWrapper_unregisteredMemeber() throws Exception {
        //given
        WrapperSelectBookRequest bookRequest =
                new WrapperSelectBookRequest(1L, "포장지 1", 3);
        List<WrapperSelectBookRequest> bookRequests = new ArrayList<>();
        bookRequests.add(bookRequest);
        WrapperSelectRequest wrapperSelectRequest = new WrapperSelectRequest(bookRequests);
        when(bookRepository.findById(1L)).thenReturn(
                Optional.ofNullable(Book.builder().title("testbook")
                        .description("설명")
                        .index("목차")
                        .pubDate(LocalDate.parse("2024-05-03"))
                        .isbn10("0123456789")
                        .isbn13("0123456789123")
                        .cost(25600)
                        .discountCost(25000)
                        .packagable(true)
                        .views(210L)
                        .status(BookStatus.FOR_SALE)
                        .stock(10)
                        .publisher(Publisher.builder().name("행복출판").build())
                        .pointRate(PointRate.builder().name("적립").earnType(EarnPointType.COST).earnPoint(300).createdAt(
                                LocalDateTime.now()).applyType(ApplyPointType.BOOK).build())
                        .build()));
        when(bookFileRepository.getBookImageUrl(1L)).thenReturn("http://img1.jpg");
        when(wrapperRepository.getCostByName("포장지 1")).thenReturn(1000);

        List<WrapperSelectBookResponse> wrapperSelectBookResponseList = new ArrayList<>();
        wrapperSelectBookResponseList.add(WrapperSelectBookResponse.builder().wrapperName("포장지 1")
                .quantity(3).bookTitle("testbook").imgUrl("포장지.img").cost(30000).discountCost(10000)
                .bookId(1L).build());
        List<String> estimatedDateList = new ArrayList<>();
        estimatedDateList.add("4/26(금)");
        estimatedDateList.add("4/29(월)");
        estimatedDateList.add("4/30(화)");
        estimatedDateList.add("5/1(수)");
        estimatedDateList.add("5/2(목)");
        OpenWrapperSelectResponse.builder().wrapperSelectResponseList(wrapperSelectBookResponseList)
                .wrapCost(3000)
                .deliveryCost(5000)
                .totalOrderCount(3)
                .estimatedDateList(estimatedDateList)
                .build();
        //when
        OpenWrapperSelectResponse response = orderService.selectWrapper(wrapperSelectRequest);

        //then
        assertEquals(3, response.getTotalOrderCount());
        WrapperSelectBookResponse wrapperSelectBookResponse = response.getWrapperSelectResponseList().get(0);
        assertEquals(bookRequest.getWrapperName(), wrapperSelectBookResponse.getWrapperName());

    }


}
