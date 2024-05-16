package shop.bookbom.shop.domain.order.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderManagementResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;

public interface OrderRepositoryCustom {
    /**
     * 주문 ID를 통해 상세조회를 하여 DTO로 변환하여 반환해주는 메서드입니다.
     *
     * @param id 주문 ID
     * @return 조회된 주문 결과
     */
    OrderDetailResponse getOrderDetailResponseById(Long id);

    /**
     * 주문 ID로 주문을 조회하는 메서드입니다.
     */
    Order getOrderFetchOrderBooksById(Long id);
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

    /**
     * ID 리스트를 통해 주문을 조회하는 메서드입니다.
     *
     * @param orderIds 주문 ID 리스트
     * @return 조회된 주문 리스트
     */
    List<Order> findAllOrdersById(List<Long> orderIds);

    List<Order> getAllOrderBeforePayment();

    List<Order> getAllOrderWaiting();

    List<Order> getAllOrderDelivering();
}
