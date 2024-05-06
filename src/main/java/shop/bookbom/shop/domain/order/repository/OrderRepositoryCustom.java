package shop.bookbom.shop.domain.order.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.order.dto.response.OrderManagementResponse;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;

public interface OrderRepositoryCustom {
    /**
     * 주문 관리를 위한 주문 내역을 불러오는 메서드입니다.
     *
     * @param pageable 페이지 정보
     * @param dateFrom 시작 날짜
     * @param dateTo   끝 날짜
     * @param sort     정렬 기준
     * @param status   주문 상태
     * @return 주문 내역
     */
    Page<OrderManagementResponse> getOrderManagement(Pageable pageable, LocalDate dateFrom, LocalDate dateTo,
                                                     String sort, OrderStatus status);
}
