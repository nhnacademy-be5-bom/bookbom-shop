package shop.bookbom.shop.domain.order.controller;

import static shop.bookbom.shop.common.DateFormatter.parseLocalDate;

import java.time.LocalDate;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.order.dto.request.OrderRequest;
import shop.bookbom.shop.domain.order.dto.request.OrderStatusUpdateRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderManagementResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.exception.OrderInfoInvalidException;
import shop.bookbom.shop.domain.order.service.OrderService;
import shop.bookbom.shop.domain.payment.dto.response.PaymentCancelResponse;
import shop.bookbom.shop.domain.users.dto.UserDto;

/**
 * 회원 주문 controller
 */
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 포장지 선택 값을 받아서 주문으로 넘겨주기 위한 api
     *
     * @param
     * @param wrapperSelectRequest(포장지 선택한 값들)
     * @param bindingResult(for validation check)
     * @return WrapperSelectResponse(포장지 선택한 값들 + 총 주문 수 + userId)
     */
    @PostMapping("/orders/wrapper")
    public CommonResponse<WrapperSelectResponse> selectWrapper(
            @Login UserDto userDto,
            @RequestBody @Valid
            WrapperSelectRequest wrapperSelectRequest, BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //포장지 선택 메소드
        WrapperSelectResponse wrapperSelectResponse =
                orderService.selectWrapperForMember(wrapperSelectRequest, userDto.getId());
        //응답 반환
        return CommonResponse.successWithData(wrapperSelectResponse);
    }

    /**
     * 회원 주문 처리 메소드
     *
     * @param orderRequest
     * @param bindingResult
     * @return 결제 요청에 필요한 데이터
     */
    @PostMapping("/orders")
    public CommonResponse<OrderResponse> processOrder(@Login UserDto userDto,
                                                      @RequestBody @Valid OrderRequest orderRequest,
                                                      BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //주문 처리 메소드
        OrderResponse orderResponse = orderService.processOrder(orderRequest, userDto.getId());
        return CommonResponse.successWithData(orderResponse);
    }

    /**
     * 관리자 페이지에서 주문 관리 내역을 조회하는 메서드입니다.
     *
     * @param pageable 페이징 정보
     * @param dateFrom 조회 시작 날짜
     * @param dateTo   조회 종료 날짜
     * @param sort     정렬 기준
     * @param status   주문 상태
     * @return 주문 관리 내역
     */
    @GetMapping("/admin/orders")
    public CommonResponse<Page<OrderManagementResponse>> adminOrderManagement(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "date_from", required = false) String dateFrom,
            @RequestParam(value = "date_to", required = false) String dateTo,
            @RequestParam(value = "sorted", required = false) String sort,
            @RequestParam(value = "status", required = false) String status) {
        LocalDate orderDateMin = parseLocalDate(dateFrom);
        LocalDate orderDateMax = parseLocalDate(dateTo);
        Page<OrderManagementResponse> orders =
                orderService.getOrderManagements(pageable, orderDateMin, orderDateMax, sort, status);
        return CommonResponse.successWithData(orders);
    }

    /**
     * 주문 상태를 변경하는 메서드입니다.
     *
     * @param request 주문 상태 변경 요청(변경할 주문 ID 리스트, 변경할 상태)
     * @return 성공 응답
     */
    @PutMapping("/admin/orders/update-status")
    public CommonResponse<Void> updateOrderStatus(@RequestBody @Valid OrderStatusUpdateRequest request) {
        orderService.updateOrderStatus(request.getOrderIds(), request.getStatus());
        return CommonResponse.success();
    }

    /**
     * 주문 상세 정보를 불러오는 메서드입니다.
     *
     * @param id 주문 id
     * @return 주문 상세 정보
     */
    @GetMapping("/orders/{id}")
    public CommonResponse<OrderDetailResponse> getOrderDetail(@PathVariable("id") Long id) {
        OrderDetailResponse orderDetailResponse = orderService.getOrderDetail(id);
        return CommonResponse.successWithData(orderDetailResponse);
    }

    @DeleteMapping("/orders/{id}")
    public CommonResponse<PaymentCancelResponse> cancelOrder(@PathVariable("id") Long id,
                                                             @RequestParam(name = "reason") String cancelReason) {
        PaymentCancelResponse paymentCancelResponse = orderService.cancelOrder(id, cancelReason);
        return CommonResponse.successWithData(paymentCancelResponse);
    }
}
