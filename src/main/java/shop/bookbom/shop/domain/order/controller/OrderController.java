package shop.bookbom.shop.domain.order.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderDetailResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.exception.OrderInfoInvalidException;
import shop.bookbom.shop.domain.order.service.OrderService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 장바구니에서 넘어온 정보로 포장지 선택 페이지 구성을 위한 책 , 포장지 정보를 불러오는 api
     *
     * @param beforeOrderRequestList(책id와 수량의 리스트)
     * @param bindingResult(for validation check)
     * @return BeforeOrderResponse(총 주문 수, 포장지 전체 리스트, 책의 수량, 이미지, 가격, 제목)
     */
    @PostMapping("/orders/before-order")
    public CommonResponse<BeforeOrderResponse> beforeOrder(
            @RequestBody @Valid BeforeOrderRequestList beforeOrderRequestList,
            BindingResult bindingResult)
            throws OrderInfoInvalidException {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //요청에서 책 정보를 받아옴
        BeforeOrderResponse beforeOrderResponse = orderService.getOrderBookInfo(beforeOrderRequestList);

        //응답 반환
        return CommonResponse.successWithData(beforeOrderResponse);
    }

    /**
     * 포장지 선택 값을 받아서 주문으로 넘겨주기 위한 api
     *
     * @param userId
     * @param wrapperSelectRequest(포장지 선택한 값들)
     * @param bindingResult(for validation check)
     * @return WrapperSelectResponse(포장지 선택한 값들 + 총 주문 수 + userId)
     */
    @PostMapping("/orders/wrapper")
    //requestparam으로 userId가 오면 회원, 안 오면 비회원(null값 들어감)
    public CommonResponse<WrapperSelectResponse> selectWrapper(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestBody @Valid
            WrapperSelectRequest wrapperSelectRequest, BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //포장지 선택 메소드
        WrapperSelectResponse wrapperSelectResponse = orderService.selectWrapper(userId, wrapperSelectRequest);
        //응답 반환
        return CommonResponse.successWithData(wrapperSelectResponse);
    }

    @GetMapping("/orders/{id}")
    public CommonResponse<OrderDetailResponse> getOrderDetail(@PathVariable("id") Long id) {
        OrderDetailResponse orderDetailResponse = orderService.getOrderDetail(id);
        return CommonResponse.successWithData(orderDetailResponse);
    }
}
