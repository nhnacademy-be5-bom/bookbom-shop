package shop.bookbom.shop.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // common
    COMMON_SYSTEM_ERROR(500, "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(400, "요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND(400, "존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS(400, "잘못된 상태값입니다."),
    // book
    BOOK_NOT_FOUND(400, "해당 책이 존재하지 않습니다."),
    // cart
    CART_INVALID_ADD_REQUEST(400, "요청한 상품 ID와 수량이 올바르지 않습니다."),
    // cart_item
    CART_ITEM_NOT_FOUND(400, "해당 장바구니 상품이 존재하지 않습니다."),
    CART_ITEM_INVALID_QUANTITY(400, "장바구니 상품 수량이 올바르지 않습니다."),
    // member
    MEMBER_NOT_FOUND(400, "해당 회원이 존재하지 않습니다."),
    // book_tag
    BOOK_TAG_NOT_FOUND(400, "해당 책에 태그가 존재하지 않습니다."),
    BOOK_TAG_ALREADY_EXIST(400, "해당 책에 태그가 이미 존재합니다."),
    //tag
    TAG_NOT_FOUND(400, "해당 태그가 존재하지 않습니다."),
    TAG_ALREADY_EXIST(400, "해당 태그가 이미 존재합니다."),
    //order
    ORDER_NOT_FOUNT(400, "해당 주문이 존재하지 않습니다."),
    //payment
    PAYMENT_VERIFY_FAIL(400, "결제 검증 실패: 요청이 유효하지 않습니다."),
    PAYMENT_NOT_ALLOWED(400, "결제가 허용되지 않습니다. 다시 시도해 주세요."),
    PAYMENT_FAILED(400, "결제가 실패했습니다. 다시 시도해 주세요."),
    PAYMENT_NOT_FOUND(404, "존재하지 않는 경제 정보입니다."),
    //paymentMethod
    PAYMENTMETHOD_NOT_FOUND(400, "해당 결제 수단이 존재하지 않습니다."),
    //orderStatus
    ORDERSTATUS_NOT_FOUND(400, "해당 주문 상태가 존재하지 않습니다."),
    //orderBook
    ORDERBOOK_NOT_FOUND(400, "해당 주문에 책정보가 존재하지 않습니다."),
    //delivery
    DELIVERY_NOT_FOUNT(400, "해당 주문의 배송 정보가 존재하지 않습니다."),
    //orderCoupon
    ORDERCOUPON_NOT_FOUND(404, "해당 주문 쿠폰이 존재하지 않습니다."),
    //memberCounpon
    MEMBERCOUPON_NOT_FOUND(404, "해원이 해당 쿠폰을 가지고 있지 않습니다."),
    MEMBERCOUPON_CANNOT_USE(400, "해당 쿠폰은 사용할 수 없습니다.");

    private final int code;
    private final String message;

}
