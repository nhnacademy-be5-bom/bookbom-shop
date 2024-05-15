package shop.bookbom.shop.domain.review.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
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
import shop.bookbom.shop.domain.review.dto.response.ReviewResponse;
import shop.bookbom.shop.domain.review.entity.Review;
import shop.bookbom.shop.domain.review.exception.ReviewOrderBookNotFoundException;
import shop.bookbom.shop.domain.review.exception.ReviewTimeLimitExceededException;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;
import shop.bookbom.shop.domain.review.service.ReviewService;
import shop.bookbom.shop.domain.reviewimage.service.ReviewImageService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
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
        validateReviewTimeLimit(order);
        Book book = findBookByOrderBooks(bookId, order.getOrderBooks());
        ApplyPointType pointType = type.equals("photo") ? ApplyPointType.REVIEW_IMAGE : ApplyPointType.REVIEW_TEXT;
        PointRate pointRate = pointRateRepository.findByApplyType(pointType)
                .orElseThrow(PointRateNotFoundException::new);
        Review review = buildReview(rating, content, book, member, pointRate);
        reviewRepository.save(review);
        if (type.equals("photo")) {
            reviewImageService.saveReviewImage(image, review);
        }
        pointHistoryService.earnPointByReview(member, type);
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviews(Long bookId, Pageable pageable) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        return reviewRepository.getAllReviewsByBook(book, pageable);
    }

    /**
     * 리뷰 엔티티를 생성하는 메서드입니다.
     *
     * @param rating    별점
     * @param content   내용
     * @param book      책
     * @param member    회원
     * @param pointRate 포인트 비율
     * @return 리뷰 엔티티
     */
    private Review buildReview(int rating, String content, Book book, Member member, PointRate pointRate) {
        return Review.builder()
                .rate(rating)
                .content(content)
                .createdAt(LocalDateTime.now())
                .book(book)
                .member(member)
                .pointRate(pointRate)
                .build();
    }

    /**
     * 주문 도서 목록에서 도서 엔티티를 찾는 메서드입니다.
     *
     * @param bookId     책 ID
     * @param orderBooks 주문 도서 목록
     * @return 도서 엔티티
     */
    private Book findBookByOrderBooks(Long bookId, List<OrderBook> orderBooks) {
        OrderBook orderBook = orderBooks.stream()
                .filter(ob -> ob.getBook().getId().equals(bookId))
                .findFirst()
                .orElseThrow(ReviewOrderBookNotFoundException::new);
        return orderBook.getBook();
    }

    /**
     * 리뷰 작성 가능 기간을 검증하는 메서드입니다.
     *
     * @param order 주문
     */
    private void validateReviewTimeLimit(Order order) {
        if (order.getOrderDate().isBefore(LocalDateTime.now().minusWeeks(2))) {
            throw new ReviewTimeLimitExceededException();
        }
    }
}
