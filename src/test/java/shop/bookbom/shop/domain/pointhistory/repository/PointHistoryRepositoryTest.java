package shop.bookbom.shop.domain.pointhistory.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getPointHistory;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

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
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistoryDetail;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;

@DataJpaTest
@Import(TestQuerydslConfig.class)
class PointHistoryRepositoryTest {
    @Autowired
    TestEntityManager em;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Test
    @DisplayName("회원 포인트 적립 내역 조회")
    void findPointHistory() {
        //given
        Role role = em.persist(getRole());
        PointRate pointRate = em.persist(getPointRate());
        Rank rank = em.persist(getRank(pointRate));
        Member member = em.persist(getMember("email@test.com", role, rank));
        for (int i = 0; i < 5; i++) {
            em.persist(getPointHistory(member, ChangeReason.EARN));
            em.persist(getPointHistory(member, ChangeReason.USE));
        }
        //when
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<PointHistoryResponse> result =
                pointHistoryRepository.getPointHistory(member, pageRequest, ChangeReason.EARN);

        //then
        PointHistoryResponse response = result.getContent().get(0);
        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(response.getReason()).isEqualTo(ChangeReason.EARN.name());
        assertThat(response.getChangePoint()).isEqualTo(100);
        assertThat(response.getDetail()).isEqualTo(PointHistoryDetail.ORDER_EARN.name());
    }

    @Test
    @DisplayName("필터링 조건 없을 때 회원 포인트 내역 조회")
    void findPointHistoryNotFiltered() {
        //given
        Role role = em.persist(getRole());
        PointRate pointRate = em.persist(getPointRate());
        Rank rank = em.persist(getRank(pointRate));
        Member member = em.persist(getMember("email@test.com", role, rank));
        for (int i = 0; i < 5; i++) {
            em.persist(getPointHistory(member, ChangeReason.EARN));
            em.persist(getPointHistory(member, ChangeReason.USE));
        }
        //when
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<PointHistoryResponse> result =
                pointHistoryRepository.getPointHistory(member, pageRequest, null);

        //then
        PointHistoryResponse response = result.getContent().get(0);
        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getTotalElements()).isEqualTo(10);
        assertThat(response.getReason()).isEqualTo(ChangeReason.EARN.name());
        assertThat(response.getChangePoint()).isEqualTo(100);
        assertThat(response.getDetail()).isEqualTo(PointHistoryDetail.ORDER_EARN.name());
    }
}
