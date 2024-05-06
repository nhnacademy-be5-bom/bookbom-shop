package shop.bookbom.shop.domain.order.service;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.OpenOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderManagementResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;

public interface OrderService {
    BeforeOrderResponse getOrderBookInfo(BeforeOrderRequestList beforeOrderRequestList);

    WrapperSelectResponse selectWrapper(WrapperSelectRequest wrapperSelectRequest);

    OrderResponse processOpenOrder(OpenOrderRequest openOrderRequest);

    /**
     * 주문 상태 관리를 위한 주문 내역을 불러오는 메서드입니다.
     *
     * @param pageable       페이지 정보
     * @param dateFrom       시작 날짜
     * @param dateTo         끝 날짜
     * @param sort           정렬 기준
     * @param deliveryStatus 주문 상태
     * @return 주문 내역
     */
    Page<OrderManagementResponse> getOrderManagements(Pageable pageable, LocalDate dateFrom, LocalDate dateTo,
                                                      String sort, String deliveryStatus);
}
