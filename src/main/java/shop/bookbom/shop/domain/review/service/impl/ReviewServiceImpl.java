package shop.bookbom.shop.domain.review.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.order.repository.OrderRepository;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;
import shop.bookbom.shop.domain.pointhistory.service.PointHistoryService;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.exception.PointRateNotFoundException;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.review.dto.response.ReviewCheckResponse;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.review.exception.ReviewOrderBookNotFoundException;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;
import shop.bookbom.shop.domain.review.service.ReviewService;
import shop.bookbom.shop.domain.reviewimage.service.ReviewImageService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PointRateRepository pointRateRepository;
    private final OrderRepository orderRepository;
    private final ReviewImageService reviewImageService;
    private final PointHistoryService pointHistoryService;

    @Override
    @Transactional
    public void createReview(Long userId, Long bookId, Long orderId, String type, int rating, String content,
                             MultipartFile image) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.getOrderFetchOrderBooksById(orderId);
        List<OrderBook> orderBooks = order.getOrderBooks();
        OrderBook orderBook = orderBooks.stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(ReviewOrderBookNotFoundException::new);
        ApplyPointType pointType = ApplyPointType.REVIEW_TEXT;
        if (type.equals("photo")) {
            pointType = ApplyPointType.REVIEW_IMAGE;
        }
        PointRate pointRate = pointRateRepository.findByApplyType(pointType)
                .orElseThrow(PointRateNotFoundException::new);
        Book book = orderBook.getBook();
        Review review = Review.builder()
                .rate(rating)
                .content(content)
                .createdAt(LocalDateTime.now())
                .book(book)
                .member(member)
                .pointRate(pointRate)
                .build();
        reviewRepository.save(review);
        if (type.equals("photo")) {
            reviewImageService.saveReviewImage(image, review);
        }
        pointHistoryService.earnPointByReview(member, type);
    }

    @Override
    public ReviewCheckResponse existsCheck(Long userId, Long bookId, Long orderId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.getOrderFetchOrderBooksById(orderId);
        List<OrderBook> orderBooks = order.getOrderBooks();
        OrderBook orderBook = orderBooks.stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(ReviewOrderBookNotFoundException::new);
        if (orderBook == null) {
            return new ReviewCheckResponse(false);
        }
        Book book = orderBook.getBook();
        boolean exists = reviewRepository.existsByMemberAndBook(member, book);
        return new ReviewCheckResponse(exists);
    }
}
