package shop.bookbom.shop.domain.reviewimage.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.common.file.ObjectService;
import shop.bookbom.shop.domain.file.entity.File;
import shop.bookbom.shop.domain.file.repository.FileRepository;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;
import shop.bookbom.shop.domain.reviewimage.entity.ReviewImage;
import shop.bookbom.shop.domain.reviewimage.repository.ReviewImageRepository;
import shop.bookbom.shop.domain.reviewimage.service.ReviewImageService;

@Service
@RequiredArgsConstructor
public class ReviewImageServiceImpl implements ReviewImageService {
    private static final String REVIEW_CONTAINER_NAME = "bookbom/review";
    private final ObjectService objectService;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewRepository reviewRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void saveReviewImage(MultipartFile image, Review review) {
        String imageId = "review_" + review.getId();
        objectService.uploadFile(image, REVIEW_CONTAINER_NAME, imageId);
        String url = objectService.getUrl(REVIEW_CONTAINER_NAME, imageId);
        File file = File.builder()
                .url(url)
                .extension(image.getContentType())
                .createdAt(LocalDateTime.now())
                .build();
        fileRepository.save(file);
        ReviewImage reviewImage = ReviewImage.builder()
                .review(review)
                .file(file)
                .build();
        reviewImageRepository.save(reviewImage);
        review.addReviewImage(reviewImage);
    }
}
