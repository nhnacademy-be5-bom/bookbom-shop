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
    ID_AND_PATH_VARIABLE_DOES_NOT_MATCH(400, "요청 ID가 요청 경로와 다릅니다"),
    // cart
    CART_INVALID_ADD_REQUEST(400, "요청한 상품 ID와 수량이 올바르지 않습니다."),
    // cart_item
    CART_ITEM_NOT_FOUND(400, "해당 장바구니 상품이 존재하지 않습니다."),
    CART_ITEM_INVALID_QUANTITY(400, "장바구니 상품 수량이 올바르지 않습니다."),
    // member
    MEMBER_NOT_FOUND(400, "해당 회원이 존재하지 않습니다."),
    // user
    USER_ALREADY_EXIST(400, "이미 존재하는 사용자입니다"),
    //wish
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
    // file
    FILE_NOT_FOUND(400, "해당 파일을 찾을 수 없습니다."),
    // author
    AUTHOR_ID_NOT_FOUND(404, "일치하는 작가 ID가 없습니다."),
    // point_rate
    POINT_RATE_NOT_FOUND(400,"해당 포인트 정책이 존재하지 않습니다."),
    ;

    private final int code;
    private final String message;
}
