package shop.bookbom.shop.domain.order.repository;

import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;

public interface OrderRepositoryCustom {
    /**
     * 주문 ID를 통해 상세조회를 하는 메서드입니다.
     * @param id 주문 ID
     * @return 조회된 주문 결과
     */
    OrderDetailResponse getOrderById(Long id);
}
