package shop.bookbom.shop.domain.wish.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.annotation.Login;
import shop.bookbom.shop.common.CommonResponse;
import shop.bookbom.shop.domain.users.dto.UserDto;
import shop.bookbom.shop.domain.wish.dto.response.WishInfoResponse;
import shop.bookbom.shop.domain.wish.exception.WishInvalidRequestException;
import shop.bookbom.shop.domain.wish.service.WishService;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    /**
     * 찜 목록에 도서를 추가합니다
     *
     * @param userDto 유저 정보
     * @param books 도서 id 리스트
     * @return 성공 응답
     */
    @PostMapping("/wish")
    public CommonResponse<Void> addWish(@Login UserDto userDto,
                                        @RequestBody List<Long> books) {
        if (!isValidRequest(books)) {
            throw new WishInvalidRequestException();
        }
        wishService.addWish(books, userDto.getId());
        return CommonResponse.success();
    }

    /**
     * 찜 목록에 있는 도서를 삭제합니다
     *
     * @param userDto 유저 정보
     * @param books 도서 id 리스트
     * @return 성공 응답
     */
    @DeleteMapping("/wish")
    public CommonResponse<Void> deleteWish(@Login UserDto userDto,
                                           @RequestBody List<Long> books) {
        if (!isValidRequest(books)) {
            throw new WishInvalidRequestException();
        }
        wishService.deleteWish(books, userDto.getId());
        return CommonResponse.success();
    }

    /**
     * 회원의 찜 목록을 조회합니다.
     *
     * @param userDto 유저 정보
     * @param pageable 페이징 처리
     * @return 회원의 찜 목록
     */
    @GetMapping("/wish")
    public CommonResponse<Page<WishInfoResponse>> getWish(@Login UserDto userDto, @PageableDefault Pageable pageable) {
        return CommonResponse.successWithData(wishService.getWishInfo(userDto.getId(), pageable));
    }

    /**
     * 추가, 삭제 request 유효성 검사
     *
     * @param books 도서 ID 리스트
     * @return 유효성 검사 결과
     */
    private boolean isValidRequest(List<Long> books) {
        return books.stream()
                .allMatch(bookId -> bookId != null
                        && bookId >= 1);
    }
}
