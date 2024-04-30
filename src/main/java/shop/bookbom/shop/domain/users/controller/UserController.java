package shop.bookbom.shop.domain.users.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.service.UserService;

@RestController
@RequestMapping("/shop/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * CREATE USER
     *
     * @param userRequestDto : String email, String password, String roleName
     * @return Long userId
     */
    @PostMapping
    public CommonResponse<Long> registerUser(@Valid @RequestBody UserRequestDto userRequestDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
        Long userId = userService.save(userRequestDto);
        return CommonResponse.successWithData(userId);
    }

    /**
     * UPDATE USER - 비밀번호 변경
     *
     * @param resetPasswordRequestDto : Long id, String password
     */
    @PatchMapping("/{id}/password")
    public CommonResponse resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        userService.resetPassword(resetPasswordRequestDto);
        return CommonResponse.success();
    }

    /**
     * UPDATE USER - REGISTERED 설정
     *
     * @param id         userId
     * @param registered
     */
    @PatchMapping("/{id}/registered")
    public CommonResponse changeRegistered(@PathVariable("id") Long id,
                                           @RequestParam boolean registered) {
        log.info("id is : " + id);
        userService.changeRegistered(id, registered);
        return CommonResponse.success();
    }

    /**
     * READ USER - REGISTERED 확인
     *
     * @param id
     * @return registered   user의 registered를 받아옴
     */
    @GetMapping("/{id}/registered")
    public CommonResponse<Boolean> getRegistered(@PathVariable Long id) {
        boolean registered = userService.isRegistered(id);
        return CommonResponse.successWithData(Boolean.valueOf(registered));
    }

    /**
     * READ USER - Email이 사용 가능한지 확인
     *
     * @param email
     * @return emial이 사용 가능한지 여부. 사용 가능하면 true
     */
    // #3-2 READ USER - Email이 사용 가능한지 확인
    @PostMapping("/email/confirm")
    public CommonResponse<Boolean> checkEmailCanUse(@RequestBody String email) {
        if (userService.checkEmailCanUse(email)) {
            return CommonResponse.successWithData(Boolean.TRUE);
        } else {
            return CommonResponse.successWithData(Boolean.FALSE);
        }
    }

    /**
     * 유저의 주문 내역을 조회하는 메서드입니다.
     *
     * @param userId   유저 아이디
     * @param pageable 페이지 정보
     * @param dateFrom 주문 최소 날짜
     * @param dateTo   주문 최대 날짜
     */
    @GetMapping("/orders")
    public CommonResponse<Page<OrderInfoResponse>> getOrders(
            @RequestParam("userId") Long userId,
            Pageable pageable,
            @RequestParam(value = "date_from", required = false) String dateFrom,
            @RequestParam(value = "date_to", required = false) String dateTo
    ) {
        OrderDateCondition orderDateCondition =
                new OrderDateCondition(parseLocalDate(dateFrom), parseLocalDate(dateTo));
        return CommonResponse.successWithData(userService.getOrderInfos(userId, pageable, orderDateCondition));
    }

    /**
     * String date를 LocalDate로 변환하는 메서드입니다.
     *
     * @param date 날짜
     * @return LocalDate
     */
    public LocalDate parseLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }
}
