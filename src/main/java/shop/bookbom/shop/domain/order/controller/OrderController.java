package shop.bookbom.shop.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.domain.book.service.BookService;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.orderbook.service.OrderBookService;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderBookService orderBookService;
    private final BookService bookService;

//    @GetMapping("/shop/{userId}/beforeOrder")//pathvariable로 userId가 오면 회원, 안 오면 비회원
//    public ResponseEntity<BeforeOrderResponse> beforeOrder_SelectWrapper(@PathVariable(required = false) Long userId,
//                                                                         @RequestBody
//                                                                         BeforeOrderRequest beforeOrderRequest) {
//        List<BeforeOrderBookRequest> BookRequests = beforeOrderRequest.getBeforeOrderBookRequests();
//        for (BeforeOrderBookRequest bookRequest : BookRequests) {
//            Optional<Book> book = bookService.getBook(bookRequest.getBookId());
//            String title = book.get().getTitle();
//            Integer cost = book.get().getCost();
//            Integer quantity = bookRequest.getQuantity();
//
//
//        }
//
//    }


}
