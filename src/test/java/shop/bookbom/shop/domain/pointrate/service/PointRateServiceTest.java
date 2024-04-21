package shop.bookbom.shop.domain.pointrate.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.pointrate.repository.dto.PointRateResponse;
import shop.bookbom.shop.domain.pointrate.service.impl.PointRateServiceImpl;

@ExtendWith(MockitoExtension.class)
class PointRateServiceTest {

    @Mock
    PointRateRepository pointRateRepository;

    @InjectMocks
    PointRateServiceImpl pointRateService;

    @Test
    @DisplayName("포인트 목록 조회")
    void getPointPolicies() {
        //given
        PointRateResponse pointRate1 = new PointRateResponse(1L, "테스트1", EarnPointType.RATE, 100);
        PointRateResponse pointRate2 = new PointRateResponse(2L, "테스트2", EarnPointType.COST, 1000);
        when(pointRateRepository.getPointPolicies()).thenReturn(List.of(pointRate1, pointRate2));

        //when
        List<PointRateResponse> result = pointRateService.getPointPolicies();
        PointRateResponse response = result.get(0);

        //then
        assertThat(result).hasSize(2);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("테스트1");
        assertThat(response.getEarnType()).isEqualTo(EarnPointType.RATE);
        assertThat(response.getEarnPoint()).isEqualTo(100);
    }
}
