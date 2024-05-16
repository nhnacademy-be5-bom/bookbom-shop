package shop.bookbom.shop.domain.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.entity.User;

public interface UserRepositoryCustom {
    /**
     * 유저의 주문 내역을 가져오는 메서드입니다.
     *
     * @param user     유저
     * @param pageable 페이지 정보
     * @return Page<OrderInfoResponse> 주문 내역
     */
    Page<OrderInfoResponse> getOrders(User user, Pageable pageable, OrderDateCondition condition);

    Long findIdByEmail(String email);
}
