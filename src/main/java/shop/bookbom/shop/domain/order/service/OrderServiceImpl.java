package shop.bookbom.shop.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import shop.bookbom.shop.domain.wrapper.dto.WrapperDto;
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
    public BeforeOrderResponse getOrderBookInfo(BeforeOrderRequestList beforeOrderRequestList) {
        int TotalOrderCount = 0;
        List<BeforeOrderBookResponse> beforeOrderBookResponseList = new ArrayList<>();
        //각 책에 대한 정보를 list로부터 가져와서 foreach문 돌림
        for (BeforeOrderRequest bookRequest : beforeOrderRequestList.getBeforeOrderRequestList()) {
            //bookId로 책 가져옴
            BookTitleAndCostResponse titleAndCostById = bookRepository.getTitleAndCostById(bookRequest.getBookId())
                    .orElseThrow(BookNotFoundException::new);
            String title = titleAndCostById.getTitle();
            Integer cost = titleAndCostById.getCost();
            Integer quantity = bookRequest.getQuantity();
            String imageUrl = bookFileRepository.getBookImageUrl(bookRequest.getBookId());

            //새로운 주문 도서 응답 빌더 생성
            BeforeOrderBookResponse beforeOrderBookResponse = BeforeOrderBookResponse.builder()
                    .bookId(bookRequest.getBookId())
                    .title(title)
                    .imageUrl(imageUrl)
                    .cost(cost)
                    .quantity(quantity)
                    .build();

            beforeOrderBookResponseList.add(beforeOrderBookResponse);
            //총 주문 개수 다 더함
            TotalOrderCount += quantity;
        }
        //모든 포장지 list 가져옴
        List<Wrapper> wrapperList = wrapperRepository.findAll();
        List<WrapperDto> wrapperDtoList = new ArrayList<>();
        for (Wrapper wrapper : wrapperList) {
            WrapperDto wrapperDto = new WrapperDto(wrapper.getId(), wrapper.getName(), wrapper.getCost());
            wrapperDtoList.add(wrapperDto);

        }
        //주문 응답 객체 생성 후 정보 저장
        return BeforeOrderResponse.builder()
                .beforeOrderBookResponseList(beforeOrderBookResponseList)
                .totalOrderCount(TotalOrderCount)
                .wrapperList(wrapperDtoList)
                .build();

    }

    @Override
    @Transactional
    public WrapperSelectResponse selectWrapper(Long userId, WrapperSelectRequest wrapperSelectRequest) {
        //request을 가져와서 총 주문 갯수와 포장지 선택 리스트를 받아옴
        int totalOrderCount = wrapperSelectRequest.getTotalOrderCount();
        List<WrapperSelectBookRequest> wrapperSelectBookRequestList =
                wrapperSelectRequest.getWrapperSelectBookRequestList();
        //포장지 셀렉 응답 리스트를 만듬
        List<WrapperSelectBookResponse> wrapperSelectBookResponseList = new ArrayList<>();
        for (WrapperSelectBookRequest selectBookRequest : wrapperSelectBookRequestList) {
            WrapperSelectBookResponse wrapperSelectBookResponse =
                    WrapperSelectBookResponse.builder()
                            .bookId(selectBookRequest.getBookId())
                            .bookTitle(selectBookRequest.getBookTitle())
                            .wrapperName(selectBookRequest.getWrapperName())
                            .imgUrl(selectBookRequest.getImgUrl())
                            .quantity(selectBookRequest.getQuantity())
                            .cost(selectBookRequest.getCost())
                            .build();
            wrapperSelectBookResponseList.add(wrapperSelectBookResponse);

        }

        //응답 반환
        return WrapperSelectResponse.builder()
                .userId(userId)
                .totalOrderCount(totalOrderCount)
                .wrapperSelectResponseList(wrapperSelectBookResponseList)
                .build();
    }
}
