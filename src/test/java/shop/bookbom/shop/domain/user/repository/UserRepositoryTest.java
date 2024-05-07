package shop.bookbom.shop.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getOrder;
import static shop.bookbom.shop.common.TestUtils.getOrderStatus;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.bookbom.shop.config.TestQuerydslConfig;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.repository.UserRepository;

@Import(TestQuerydslConfig.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    TestEntityManager em;

    @Autowired
    UserRepository userRepository;

    Member member;

    @BeforeEach
    void setUp() {
        Role role = em.persist(getRole());
        PointRate pointRate = em.persist(getPointRate());
        Rank rank = em.persist(getRank(pointRate));
        member = em.persist(getMember("email@test.com", role, rank));
        OrderStatus orderStatus = em.persist(getOrderStatus());
        for (int i = 1; i <= 14; i++) {
            em.persist(getOrder(member, orderStatus, LocalDateTime.now().minusDays(i)));
        }
    }

    @Test
    @DisplayName("주문 내역 조회")
    void getOrders() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        OrderDateCondition condition = new OrderDateCondition();

        //when
        Page<OrderInfoResponse> result = userRepository.getOrders(member, pageRequest, condition);

        //then
        assertThat(result.getContent()).hasSize(5);
        assertThat(result.getTotalPages()).isEqualTo(3);
        assertThat(result.getTotalElements()).isEqualTo(14);
    }

    @Test
    @DisplayName("주문 내역 조회 - 날짜 조건")
    void getOrdersWithDateCondition() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);
        LocalDate min = LocalDate.now().minusDays(10);
        LocalDate max = LocalDate.now();
        OrderDateCondition condition = new OrderDateCondition(min, max);

        //when
        Page<OrderInfoResponse> result = userRepository.getOrders(member, pageRequest, condition);

        //then
        assertThat(result.getContent()).hasSize(5);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getTotalElements()).isEqualTo(10);
    }
}
