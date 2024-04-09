package shop.bookbom.shop.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderBookResponse;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.BookTitleAndCostResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.wrapper.dto.response.WrapperResponse;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.repository.WrapperRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final WrapperRepository wrapperRepository;


    @Override
    @Transactional(readOnly = true)
    public BeforeOrderResponse getOrderBookInfo(List<BeforeOrderRequest> beforeOrderRequestList) {
        int TotalOrderCount = 0;
        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        //각 책에 대한 정보를 list로부터 가져와서 foreach문 돌림
        for (BeforeOrderRequest bookRequest : beforeOrderRequestList) {
            //bookId로 책 가져옴
            BookTitleAndCostResponse titleAndCostById = bookRepository.getTitleAndCostById(bookRequest.getBookId());
            //책 정보가 존재하면
            if (Objects.nonNull(titleAndCostById)) {
                //책 정보를 가져와서 응답 객체에 넣음
                String title = titleAndCostById.getTitle();
                Integer cost = titleAndCostById.getCost();
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
        List<WrapperResponse> wrapperResponseList = new ArrayList<>();
        for (Wrapper wrapper : wrapperList) {
            WrapperResponse wrapperResponse = WrapperResponse.builder().id(wrapper.getId())
                    .name(wrapper.getName())
                    .cost(wrapper.getCost()).build();
            wrapperResponseList.add(wrapperResponse);

        }
        //주문 응답 객체 생성 후 정보 저장
        return BeforeOrderResponse.builder()
                .beforeOrderBookResponseList(beforeOrderBookResponseList)
                .totalOrderCount(TotalOrderCount)
                .wrapperList(wrapperResponseList)
                .build();

    }

    @Override
    @Transactional
    public WrapperSelectResponse selectWrapper(Long userId, WrapperSelectRequest wrapperSelectRequest) {
        int totalOrderCount = wrapperSelectRequest.getTotalOrderCount();
        List<WrapperSelectBookRequest> wrapperSelectBookRequestList =
                wrapperSelectRequest.getWrapperSelectBookRequestList();

        return WrapperSelectResponse.builder()
                .userId(userId)
                .totalOrderCount(totalOrderCount)
                .wrapperSelectRequestList(wrapperSelectBookRequestList)
                .build();

    }
}
