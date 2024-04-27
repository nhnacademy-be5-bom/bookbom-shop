package shop.bookbom.shop.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;

@Getter
public class MemberInfoResponse {
    private Long id;
    private String nickname;
    private String rank;
    private List<OrderInfoResponse> lastOrders;
    private int point;
    private long couponCount;
    private long wishCount;

    @Builder
    @QueryProjection
    public MemberInfoResponse(
            Long id,
            String nickname,
            String rank,
            List<OrderInfoResponse> lastOrders,
            int point,
            long couponCount,
            long wishCount
    ) {
        this.id = id;
        this.nickname = nickname;
        this.rank = rank;
        this.lastOrders = lastOrders;
        this.point = point;
        this.couponCount = couponCount;
        this.wishCount = wishCount;
    }
}
