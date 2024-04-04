package shop.bookbom.shop.domain.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.service.BookService;
import shop.bookbom.shop.domain.bookfile.service.BookFileService;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.orderbook.service.OrderBookService;
import shop.bookbom.shop.domain.users.service.UserService;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.service.WrapperService;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderBookService orderBookService;
    private final BookService bookService;
    private final BookFileService bookFileService;
    private final WrapperService wrapperService;

    @GetMapping("/shop/{userId}/beforeOrder")
    //pathvariable로 userId가 오면 회원, 안 오면 비회원
    public CommonResponse<BeforeOrderResponse> showSelectWrapper(@PathVariable(required = false) Long userId,
                                                                 @RequestBody
                                                                 List<BeforeOrderRequest> beforeOrderRequestList) {
        //요청에서 책 정보를 받아옴

        int TotalOrderCount = 0;
        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        //각 책에 대한 정보를 list로부터 가져와서 foreach문 돌림
        for (BeforeOrderRequest bookRequest : beforeOrderRequestList) {
            //bookId로 책 가져옴
            Optional<Book> book = bookService.getBook(bookRequest.getBookId());
            //책 정보가 존재하면
            if (book.isPresent()) {
                //새로운 주문 도서 응답 객체 생성
                BeforeOrderBookResponse beforeOrderBookResponse = new BeforeOrderBookResponse();
                //책 정보를 가져와서 응답 객체에 넣음
                String title = book.get().getTitle();
                Integer cost = book.get().getCost();
                Integer quantity = bookRequest.getQuantity();
                String imageUrl = bookFileService.find_bookImgUrl(bookRequest.getBookId());

                beforeOrderBookResponse.setTitle(title);
                beforeOrderBookResponse.setCost(cost);
                beforeOrderBookResponse.setQuantity(quantity);
                beforeOrderBookResponse.setImageUrl(imageUrl);
                beforeOrderBookResponseList.add(beforeOrderBookResponse);
                //총 주문 개수 다 더함
                TotalOrderCount += quantity;
            }


        }
        //모든 포장지 list 가져옴
        List<Wrapper> wrapperList = wrapperService.getAllWrapper();

        //주문 응답 객체 생성 후 정보 저장
        BeforeOrderResponse beforeOrderResponse = new BeforeOrderResponse();
        beforeOrderResponse.setBeforeOrderBookResponses(beforeOrderBookResponseList);
        beforeOrderResponse.setTotalOrderCount(TotalOrderCount);
        beforeOrderResponse.setWrapperList(wrapperList);

        //응답 반환
        return CommonResponse.successWithData(beforeOrderResponse);
    }

    @PostMapping("/shop/{userId}/beforeOrder")
    public CommonResponse<WrapperSelectResponse> setSelectWrapper(@PathVariable(required = false) Long userId,
                                                                  @RequestBody
                                                                  List<WrapperSelectRequest> wrapperSelectRequestList) {

    }
}
