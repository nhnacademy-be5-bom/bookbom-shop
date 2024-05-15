package shop.bookbom.shop.domain.review.service;

import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.review.dto.response.ReviewCheckResponse;

public interface ReviewService {
    /**
     * 리뷰를 생성하는 메서드입니다.
     *
     * @param userId  사용자 ID
     * @param bookId  도서 ID
     * @param orderId 주문 ID
     * @param type    리뷰 타입
     * @param rating  별점
     * @param content 리뷰 내용
     * @param image   이미지 파일
     */
    void createReview(Long userId, Long bookId, Long orderId, String type, int rating, String content, MultipartFile image);

    /**
     * 해당 도서에 대해 이미 작성한 리뷰가 있는지 체크하는 메서드입니다.
     * @param userId 사용자 ID
     * @param bookId 도서 ID
     * @param orderId 주문 ID
     * @return ReviewCheckResponse(리뷰가 존재하는지 여부)
     */
    ReviewCheckResponse existsCheck(Long userId, Long bookId, Long orderId);
}
