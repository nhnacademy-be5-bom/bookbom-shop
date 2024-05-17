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
    BOOK_NOT_FOUND(404, "해당 책이 존재하지 않습니다."),
    ID_AND_PATH_VARIABLE_DOES_NOT_MATCH(403, "요청 ID가 요청 경로와 다릅니다."),
    EXCEEDS_OFFSET_RANGE(403, "요청한 페이지 번호가 마지막 페이지보다 큽니다."),
    BOOK_NOT_FOR_SALE(400, "해당 책은 판매상태가 아닙니다. 다시 시도해주세요"),
    // cart
    CART_INVALID_ADD_REQUEST(400, "요청한 상품 ID와 수량이 올바르지 않습니다."),
    // cart_item
    CART_ITEM_NOT_FOUND(400, "해당 장바구니 상품이 존재하지 않습니다."),
    CART_ITEM_INVALID_QUANTITY(400, "장바구니 상품 수량이 올바르지 않습니다."),
    // member
    MEMBER_NOT_FOUND(400, "해당 회원이 존재하지 않습니다."),
    // user
    USER_ALREADY_EXIST(400, "이미 존재하는 사용자입니다"),
    USER_NOT_VALIDATE(400, "아이디나 비밀번호가 일치하지 않습니다."),
    CONFIRM_PASSWORD_NOT_MATCH(400, "비밀번호 확인이 일치하지 않습니다."),
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    // wish
    WISH_NOT_FOUND(400, "해당 찜 상품이 존재하지 않습니다."),
    WISH_DUPLICATE_VALUE(400, "이미 찜한 상품입니다."),
    // category
    CATEGORY_NAME_NOT_FOUND(400, "입력하신 카테고리는 존재하지 않습니다."),
    CATEGORY_STILL_HAS_CHILD_LEFT(403, "해당 카테고리에 하위 카테고리가 남아있습니다."),
    CATEGORY_STILL_HAS_BOOK_LEFT(403, "해당 카테고리에 속한 도서가 남아있습니다."),
    // book_tag
    BOOK_TAG_NOT_FOUND(400, "해당 책에 태그가 존재하지 않습니다."),
    BOOK_TAG_ALREADY_EXIST(400, "해당 책에 태그가 이미 존재합니다."),
    //tag
    TAG_NOT_FOUND(400, "해당 태그가 존재하지 않습니다."),
    TAG_ALREADY_EXIST(400, "해당 태그가 이미 존재합니다."),
    //order
    ORDER_NOT_FOUND(400, "해당 주문이 존재하지 않습니다."),
    LOW_STOCK(400, "주문하려는 책의 재고가 부족합니다. 다시 시도해주세요"),
    //payment
    PAYMENT_VERIFY_FAIL(400, "결제 검증 실패: 요청이 유효하지 않습니다."),
    PAYMENT_NOT_ALLOWED(400, "결제가 허용되지 않습니다. 다시 시도해 주세요."),
    PAYMENT_FAILED(400, "결제가 실패했습니다. 다시 시도해 주세요."),
    PAYMENT_NOT_FOUND(404, "존재하지 않는 결제 정보입니다."),
    PAYMENT_CANCEL_FAILED(400, "결제 취소가 실패했습니다. 다시 시도해주세요"),
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
    MEMBERCOUPON_CANNOT_USE(400, "해당 쿠폰은 사용할 수 없습니다."),
    // file
    FILE_NOT_FOUND(400, "해당 파일을 찾을 수 없습니다."),
    // author
    AUTHOR_ID_NOT_FOUND(404, "일치하는 작가 ID가 없습니다."),
    // point_rate
    POINT_RATE_NOT_FOUND(400, "해당 포인트 정책이 존재하지 않습니다."),
    // point_history
    POINT_INVALID_REASON(400, "요청하신 포인트 변동사유가 올바르지 않습니다."),
    //orderstatus
    ORDER_STATUS_NOT_FOUND(404, "해당 주문 상태가 존재하지 않습니다."),
    //wrapper
    WRAPPER_NOT_FOUND(404, "해당 포장지가 존재하지 않습니다."),
    //coupon
    COUPON_NOT_FOUND(400, "해당 쿠폰이 존재하지 않습니다."),
    JWT_NOT_VALIDATE(400, "유효하지 않은 상태입니다."),
    //rank
    RANK_NOT_FOUND(404, "해당 등급이 존재하지 않습니다."),
    //address
    ADDRESS_NOT_FOUND(404, "해당 주소가 존재하지 않습니다."),
    ADDRESS_LIMIT_EXCEED(400, "주소록 최대 저장 갯수를 초과하였습니다."),
    ADDRESS_ALREADY_EXIST(400, "이미 동일한 주소가 존재합니다."),
    ADDRESS_DEFAULT_DELETE(400, "기본 주소지는 삭제할 수 없습니다."),
    ADDRESS_MINIMUM_REQUIRED(400, "주소록에 최소 1개 이상의 주소가 있어야 합니다."),
    // review
    REVIEW_ORDER_BOOK_NOT_FOUND(400, "주문하지 않은 도서는 리뷰할 수 없습니다."),
    REVIEW_NOT_FOUND(404, "해당 리뷰가 존재하지 않습니다."),
    REVIEW_TIME_LIMIT_EXCEED(400, "리뷰 작성 가능 기간이 지났습니다."),
    ;

    private final int code;
    private final String message;
}
