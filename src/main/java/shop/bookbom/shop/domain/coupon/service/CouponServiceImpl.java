package shop.bookbom.shop.domain.coupon.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.category.entity.Category;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;
import shop.bookbom.shop.domain.coupon.dto.request.AddBookCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCategoryCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.request.AddCouponRequest;
import shop.bookbom.shop.domain.coupon.dto.response.CouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponInfoResponse;
import shop.bookbom.shop.domain.coupon.dto.response.MyCouponRecordResponse;
import shop.bookbom.shop.domain.coupon.entity.Coupon;
import shop.bookbom.shop.domain.coupon.entity.CouponType;
import shop.bookbom.shop.domain.coupon.exception.CouponNotFoundException;
import shop.bookbom.shop.domain.coupon.repository.CouponRepository;
import shop.bookbom.shop.domain.couponbook.entity.CouponBook;
import shop.bookbom.shop.domain.couponbook.repository.CouponBookRepository;
import shop.bookbom.shop.domain.couponcategory.entity.CouponCategory;
import shop.bookbom.shop.domain.couponcategory.repository.CouponCategoryRepository;
import shop.bookbom.shop.domain.couponpolicy.entity.CouponPolicy;
import shop.bookbom.shop.domain.couponpolicy.exception.CouponPolicyNotFoundException;
import shop.bookbom.shop.domain.couponpolicy.repository.CouponPolicyRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.exception.MemberNotFoundException;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.membercoupon.dto.request.IssueCouponRequest;
import shop.bookbom.shop.domain.membercoupon.entity.CouponStatus;
import shop.bookbom.shop.domain.membercoupon.entity.MemberCoupon;
import shop.bookbom.shop.domain.membercoupon.repository.MemberCouponRepository;
import shop.bookbom.shop.domain.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final CouponBookRepository couponBookRepository;
    private final CouponCategoryRepository couponCategoryRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final UserRepository userRepository;

    @Override
    public void addGeneralCoupon(AddCouponRequest addCouponRequest) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(addCouponRequest.getCouponPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);
        Coupon coupon = Coupon.builder()
                .name(addCouponRequest.getName())
                .type(CouponType.GENERAL)
                .couponPolicy(couponPolicy)
                .build();
        couponRepository.save(coupon);
    }

    @Override
    public void addBookCoupon(AddBookCouponRequest addBookCouponRequest) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(addBookCouponRequest.getCouponPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);

        Book book = bookRepository.findById(addBookCouponRequest.getBookId())
                .orElseThrow(BookNotFoundException::new);

        Coupon coupon = Coupon.builder()
                .name(addBookCouponRequest.getName())
                .type(CouponType.BOOK)
                .couponPolicy(couponPolicy)
                .build();

        couponRepository.save(coupon);

        CouponBook couponBook = CouponBook.builder()
                .book(book)
                .coupon(coupon)
                .build();

        couponBookRepository.save(couponBook);
    }

    @Override
    public void addCategoryCoupon(AddCategoryCouponRequest addCategoryCouponRequest) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(addCategoryCouponRequest.getCouponPolicyId())
                .orElseThrow(CouponPolicyNotFoundException::new);

        Category category = categoryRepository.findById(addCategoryCouponRequest.getCategoryId())
                .orElseThrow();

        Coupon coupon = Coupon.builder()
                .name(addCategoryCouponRequest.getName())
                .type(CouponType.CATEGORY)
                .couponPolicy(couponPolicy)
                .build();

        couponRepository.save(coupon);

        CouponCategory couponCategory = CouponCategory.builder()
                .category(category)
                .coupon(coupon)
                .build();

        couponCategoryRepository.save(couponCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponInfoResponse> getCouponInfo(Pageable pageable, String type) {
        return couponRepository.getCouponInfoList(pageable, CouponType.valueOf(type));
    }

    @Override
    public void addMemberCoupon(IssueCouponRequest issueCouponRequest) {
        Coupon coupon = couponRepository.findById(issueCouponRequest.getCouponId())
                .orElseThrow(CouponNotFoundException::new);
        issueCouponRequest.getUserEmailList().forEach(email -> {
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(MemberNotFoundException::new);
            //같은 쿠폰을 가지고 있을 때 예외처리?
            MemberCoupon memberCoupon = MemberCoupon.builder()
                    .status(CouponStatus.NEW)
                    .issueDate(LocalDate.now())
                    .expireDate(issueCouponRequest.getExpireDate())
                    .coupon(coupon)
                    .member(member)
                    .build();
            memberCouponRepository.save(memberCoupon);
        });
    }

    @Override
    public void addWelcomeCoupon(String email) {
        //welcom coupon id 2로 가정
        Coupon welcomCoupon = couponRepository.findById(2L)
                .orElseThrow(CouponNotFoundException::new);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        MemberCoupon memberCoupon = MemberCoupon.builder()
                .status(CouponStatus.NEW)
                .issueDate(LocalDate.now())
                .expireDate(LocalDate.now().plusDays(30))
                .coupon(welcomCoupon)
                .member(member)
                .build();
        memberCouponRepository.save(memberCoupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MyCouponInfoResponse> getMemberCouponInfo(Pageable pageable, Long userId) {
        return memberCouponRepository.getMyCouponInfo(pageable, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MyCouponRecordResponse> getMemberRecodes(Pageable pageable, Long userId) {
        List<MyCouponRecordResponse> records = new ArrayList<>();
        final long[] total = {0};
        memberCouponRepository.getMyCouponRecordList(userId).forEach(record -> {
                    if (record.getStatus().getValue().equals("사용 완료")) {
                        records.add(MyCouponRecordResponse.of(record.getName(), CouponStatus.NEW, record.getIssueDate()));
                        records.add(MyCouponRecordResponse.of(record.getName(), CouponStatus.USED, record.getUseDate()));
                        total[0] += 2;
                    } else if (record.getStatus().getValue().equals("만료")) {
                        if (LocalDate.now().isAfter(record.getExpireDate())) {
                            //expireDate가 지나지 않았는데 상태가 expired인 경우 예외처리
                            return;
                        }
                        records.add(MyCouponRecordResponse.of(record.getName(), CouponStatus.NEW, record.getIssueDate()));
                        records.add(MyCouponRecordResponse.of(record.getName(), CouponStatus.EXPIRED, record.getExpireDate()));
                        total[0] += 2;
                    } else { //NEW
                        records.add(MyCouponRecordResponse.of(record.getName(), CouponStatus.NEW, record.getIssueDate()));
                        total[0]++;
                    }
                }
        );
        //sorting
        List<MyCouponRecordResponse> sortedRecords = records.stream()
                .sorted(Comparator.comparing(MyCouponRecordResponse::getDate).reversed())
                .collect(Collectors.toList());
        //repository 다녀오기
        return memberCouponRepository.getMyCouponRecordPage(pageable, sortedRecords, total[0]);
    }
}
