package shop.bookbom.shop.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static shop.bookbom.shop.common.TestUtils.getBook;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getOrder;
import static shop.bookbom.shop.common.TestUtils.getOrderStatus;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getPublisher;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;
import static shop.bookbom.shop.common.TestUtils.getWish;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import shop.bookbom.shop.config.TestQuerydslConfig;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;

@Import(TestQuerydslConfig.class)
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    TestEntityManager em;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("마이 페이지 회원 정보 조회")
    void findMemberInfo() {
        Role role = em.persist(getRole());
        PointRate pointRate = em.persist(getPointRate());
        Rank rank = em.persist(getRank(pointRate));
        OrderStatus orderStatus = em.persist(getOrderStatus());
        Member member = em.persist(getMember("email@email.com", role, rank));
        Order order1 = em.persist(getOrder(member, orderStatus, LocalDateTime.now()));
        Order order2 = em.persist(getOrder(member, orderStatus, LocalDateTime.now()));
        member.addOrder(order1);
        member.addOrder(order2);
        Publisher publisher = em.persist(getPublisher());
        Book book1 = em.persist(getBook("title1", pointRate, publisher));
        Book book2 = em.persist(getBook("title2", pointRate, publisher));
        em.persist(getWish(member, book1));
        em.persist(getWish(member, book2));
        //when
        MemberInfoResponse response = memberRepository.findMemberInfo(member.getId());
        //then
        assertThat(response.getRank()).isEqualTo(rank.getName());
        assertThat(response.getNickname()).isEqualTo(member.getNickname());
        assertThat(response.getPoint()).isEqualTo(member.getPoint());
        assertThat(response.getLastOrders()).hasSize(2);
        assertThat(response.getWishCount()).isEqualTo(2);
        assertThat(response.getCouponCount()).isZero();
    }

    @Test
    @DisplayName("존재하지 않는 회원 정보 요청")
    void findNoneMemberInfo() {
        //when & then
        assertThatThrownBy(() -> memberRepository.findMemberInfo(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }
}
