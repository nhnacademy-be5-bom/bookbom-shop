package shop.bookbom.shop.domain.order.service;

import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;

public interface OrderService {

    /**
     * 주문 전에 bookId로 책 정보를 불러오고 포장지 전체 List를 받아오는 메소드
     *
     * @param beforeOrderRequestList(bookId,quantity)
     * @return 책 제목, 책 이미지, 수량, 가격 그리고 전체 주문 갯수, 포장지 전체 List
     */
    BeforeOrderResponse getOrderBookInfo(BeforeOrderRequestList beforeOrderRequestList);

    /**
     * 포장지 선택 정보를 처리하는 메소드
     *
     * @param userId
     * @param wrapperSelectRequest(책정보와 선택한 포장지 의 List와 전체 주문 갯수)
     * @return 포장지 선택 request 와 userId를 내보냄
     */
    WrapperSelectResponse selectWrapper(Long userId, WrapperSelectRequest wrapperSelectRequest);
}
