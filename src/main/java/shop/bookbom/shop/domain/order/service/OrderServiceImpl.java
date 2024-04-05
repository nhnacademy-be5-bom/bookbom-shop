package shop.bookbom.shop.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.repository.WrapperRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final WrapperRepository wrapperRepository;


    @Override
    public BeforeOrderResponse getOrderBookInfo(List<BeforeOrderRequest> beforeOrderRequestList) {
        int TotalOrderCount = 0;
        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        //각 책에 대한 정보를 list로부터 가져와서 foreach문 돌림
        for (BeforeOrderRequest bookRequest : beforeOrderRequestList) {
            //bookId로 책 가져옴
            Optional<Book> book = bookRepository.findById(bookRequest.getBookId());
            //책 정보가 존재하면
            if (book.isPresent()) {


                //책 정보를 가져와서 응답 객체에 넣음
                String title = book.get().getTitle();
                Integer cost = book.get().getCost();
                Integer quantity = bookRequest.getQuantity();
                String imageUrl = bookFileRepository.getBookImageUrl(bookRequest.getBookId());

                //새로운 주문 도서 응답 빌더 생성
                BeforeOrderBookResponse beforeOrderBookResponse = BeforeOrderBookResponse.builder()
                        .title(title)
                        .imageUrl(imageUrl)
                        .cost(cost)
                        .quantity(quantity)
                        .build();

                beforeOrderBookResponseList.add(beforeOrderBookResponse);
                //총 주문 개수 다 더함
                TotalOrderCount += quantity;
            } else {
                throw new BookNotFoundException();
            }


        }
        //모든 포장지 list 가져옴
        List<Wrapper> wrapperList = wrapperRepository.findAll();

        //주문 응답 객체 생성 후 정보 저장
        return BeforeOrderResponse.builder()
                .beforeOrderBookResponseList(beforeOrderBookResponseList)
                .totalOrderCount(TotalOrderCount)
                .wrapperList(wrapperList)
                .build();

    }

    @Override
    public WrapperSelectResponse selectWrapper(Long userId, WrapperSelectRequest wrapperSelectRequest) {
        int totalOrderCount = wrapperSelectRequest.getTotalOrderCount();
        List<WrapperSelectBookRequest> wrapperSelectBookRequestList =
                wrapperSelectRequest.getWrapperSelectBookRequestList();

        return WrapperSelectResponse.builder()
                .userId(userId)
                .totalCount(totalOrderCount)
                .wrapperSelectRequestList(wrapperSelectBookRequestList)
                .build();

    }
}
