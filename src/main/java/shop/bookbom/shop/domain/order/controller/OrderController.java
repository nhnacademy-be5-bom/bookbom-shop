package shop.bookbom.shop.domain.order.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.order.dto.request.BeforeOrderRequest;
import shop.bookbom.shop.domain.order.dto.request.WrapperSelectRequest;
import shop.bookbom.shop.domain.order.dto.response.BeforeOrderResponse;
import shop.bookbom.shop.domain.order.dto.response.WrapperSelectResponse;
import shop.bookbom.shop.domain.order.exception.OrderInfoInvalidException;
import shop.bookbom.shop.domain.order.service.OrderService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/orders/before-order")
    public CommonResponse<BeforeOrderResponse> beforeOrder(@Valid @RequestBody
                                                           List<BeforeOrderRequest> beforeOrderRequestList,
                                                           BindingResult bindingResult) {
        //요청의 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new OrderInfoInvalidException();
        }
        //요청에서 책 정보를 받아옴
        BeforeOrderResponse beforeOrderResponse = orderService.getOrderBookInfo(beforeOrderRequestList);

        //응답 반환
        return CommonResponse.successWithData(beforeOrderResponse);
    }

    @PostMapping("/orders/wrapper")
    //requestparam으로 userId가 오면 회원, 안 오면 비회원(null값 들어감)
    public CommonResponse<WrapperSelectResponse> selectWrapper(
            @RequestParam(name = "userId", required = false) Long userId,
            @Valid @RequestBody
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
}
