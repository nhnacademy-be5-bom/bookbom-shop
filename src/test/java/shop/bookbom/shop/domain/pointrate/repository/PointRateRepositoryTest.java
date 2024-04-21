package shop.bookbom.shop.domain.pointrate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.bookbom.shop.config.TestQuerydslConfig;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;

@Import(TestQuerydslConfig.class)
@DataJpaTest
class PointRateRepositoryTest {
    @Autowired
    PointRateRepository pointRateRepository;

    @Test
    @DisplayName("포인트 정책 조회")
    void getPointPolicies() {
        // given
        PointRate pointRate1 = PointRate.builder()
                .name("테스트1")
                .earnType(EarnPointType.COST)
                .earnPoint(100)
                .applyType(ApplyPointType.BOOK)
                .createdAt(LocalDateTime.now())
                .build();
        PointRate pointRate2 = PointRate.builder()
                .name("테스트2")
                .earnType(EarnPointType.RATE)
                .earnPoint(300)
                .applyType(ApplyPointType.RANK)
                .createdAt(LocalDateTime.now())
                .build();
        pointRateRepository.save(pointRate1);
        pointRateRepository.save(pointRate2);

        // when
        List<PointRateResponse> pointPolicies = pointRateRepository.getPointPolicies();

        //then
        PointRateResponse response = pointPolicies.get(0);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(pointPolicies).hasSize(2);
        assertThat(response.getName()).isEqualTo("테스트1");
        assertThat(response.getEarnType()).isEqualTo(EarnPointType.COST);
        assertThat(response.getEarnPoint()).isEqualTo(100);
    }
}
