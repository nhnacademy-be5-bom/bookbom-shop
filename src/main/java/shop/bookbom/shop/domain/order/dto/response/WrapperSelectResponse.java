package shop.bookbom.shop.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.membercoupon.dto.MemberCouponDto;

@Getter
public class WrapperSelectResponse {
    private int totalOrderCount;
    private List<WrapperSelectBookResponse> wrapperSelectResponseList;
    private List<String> estimatedDateList;
    private int wrapCost;
    private int point;
    private List<MemberCouponDto> availableMemberCoupons;
    private List<MemberCouponDto> unavailableMemberCoupons;

    @Builder
    public WrapperSelectResponse(int totalOrderCount, List<WrapperSelectBookResponse> wrapperSelectResponseList,
                                 List<String> estimatedDateList, int wrapCost, int point,
                                 List<MemberCouponDto> availableMemberCoupons,
                                 List<MemberCouponDto> unavailableMemberCoupons) {
        this.totalOrderCount = totalOrderCount;
        this.wrapperSelectResponseList = wrapperSelectResponseList;
        this.estimatedDateList = estimatedDateList;
        this.wrapCost = wrapCost;
        this.point = point;
        this.availableMemberCoupons = availableMemberCoupons;
        this.unavailableMemberCoupons = unavailableMemberCoupons;
    }
}
