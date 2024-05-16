package shop.bookbom.shop.domain.reviewimage.service;

import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.review.entity.Review;

public interface ReviewImageService {
    /**
     * 리뷰 이미지를 저장하는 메서드입니다.
     *
     * @param image  이미지 파일
     * @param review 리뷰
     */
    void saveReviewImage(MultipartFile image, Review review);
}
