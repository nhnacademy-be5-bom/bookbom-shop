package shop.bookbom.shop.domain.order.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequestList;
import shop.bookbom.shop.domain.order.dto.request.OpenOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectBookRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.OrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.exception.OrderInfoInvalidException;
import shop.bookbom.shop.domain.order.service.OrderService;

@RestController
@RequestMapping("/shop/open")
@RequiredArgsConstructor
public class OpenOrderController {
    private final OrderService orderService;

    /**
     * 장바구니에서 넘어온 정보로 포장지 선택 페이지 구성을 위한 책 , 포장지 정보를 불러오는 api
     *
     * @param beforeOrderRequestList(책id와 수량의 리스트)
     * @param bindingResult(for validation check)
     * @return BeforeOrderResponse(총 주문 수, 포장지 전체 리스트, 책의 수량, 이미지, 가격, 제목)
     */
    @PostMapping("/orders/before-order")
    public CommonResponse<?> beforeOrder(
            @RequestBody @Valid BeforeOrderRequestList beforeOrderRequestList,
            BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        for (BeforeOrderRequest beforeOrderRequest : beforeOrderRequestList.getBeforeOrderRequests()) {
            if (!orderService.checkStock(beforeOrderRequest.getBookId(), beforeOrderRequest.getQuantity())) {
                return CommonResponse.fail(ErrorCode.LOW_STOCK);
            }
        }
        //요청에서 책 정보를 받아옴
        BeforeOrderResponse beforeOrderResponse = orderService.getOrderBookInfo(beforeOrderRequestList);

        //응답 반환
        return CommonResponse.successWithData(beforeOrderResponse);
    }

    @PostMapping("/orders/wrapper")
    public CommonResponse<WrapperSelectResponse> selectWrapper(
            @RequestBody @Valid
            WrapperSelectRequest wrapperSelectRequest, BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //포장지 선택 메소드
        WrapperSelectResponse wrapperSelectResponse = orderService.selectWrapper(wrapperSelectRequest);
        //응답 반환
        return CommonResponse.successWithData(wrapperSelectResponse);
    }

    @PostMapping("/orders")
    public CommonResponse<?> processOrder(@RequestBody @Valid OpenOrderRequest openOrderRequest,
                                          BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        OrderResponse orderResponse = orderService.processOpenOrder(openOrderRequest);
        for (WrapperSelectBookRequest bookRequest : openOrderRequest.getWrapperSelectRequestList()) {
            if (!orderService.checkStock(bookRequest.getBookId(), bookRequest.getQuantity())) {
                return CommonResponse.fail(ErrorCode.LOW_STOCK);
            }
            orderService.decreaseStock(bookRequest.getBookId(), bookRequest.getQuantity());
        }

        return CommonResponse.successWithData(orderResponse);
    }

}
