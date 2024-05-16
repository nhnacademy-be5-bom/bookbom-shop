package shop.bookbom.shop.domain.reviewimage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.reviewimage.entity.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
