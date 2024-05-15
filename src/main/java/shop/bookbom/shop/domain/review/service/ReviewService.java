package shop.bookbom.shop.domain.review.service;

import org.springframework.web.multipart.MultipartFile;

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
}
