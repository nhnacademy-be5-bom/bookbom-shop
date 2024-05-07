package shop.bookbom.shop.domain.order.service;

import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.OpenOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;

public interface OrderService {


    BeforeOrderResponse getOrderBookInfo(BeforeOrderRequestList beforeOrderRequestList);


    WrapperSelectResponse selectWrapper(WrapperSelectRequest wrapperSelectRequest);


    OrderResponse processOpenOrder(OpenOrderRequest openOrderRequest);

    /**
     * 주문 상세 정보를 불러오는 메서드입니다.
     *
     * @param id 주문 id
     * @return 주문 상세 정보
     */
    OrderDetailResponse getOrderDetail(Long id);
}
