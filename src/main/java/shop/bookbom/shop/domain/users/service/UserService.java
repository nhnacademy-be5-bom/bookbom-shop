package shop.bookbom.shop.domain.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.dto.request.EmailPasswordDto;
import shop.bookbom.shop.domain.users.dto.request.SetPasswordRequest;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.dto.response.UserIdRole;

public interface UserService {

    Long save(UserRequestDto userRequestDto);

    void changeRegistered(Long id, boolean registered);

    boolean isRegistered(Long id);

    boolean checkEmailCanUse(String email);

    boolean confirm(EmailPasswordDto emailPasswordDto);

    UserIdRole getIdRole(EmailPasswordDto emailPasswordDto);

    /**
     * 회원의 주문 내역을 가져오는 메서드입니다.
     *
     * @param userId 회원 ID
     * @return Page<OrderInfoResponse> 주문 내역
     */
    Page<OrderInfoResponse> getOrderInfos(Long userId, Pageable pageable, OrderDateCondition condition);

    void editPw(Long id, SetPasswordRequest setPasswordRequest);

    /**
     * 비회원의 마이페이지를 가져오는 메서드입니다.
     *
     * @param id 비회원 ID
     * @return MemberInfoResponse 비회원 정보
     */
    MemberInfoResponse getMyPage(Long id);
}
