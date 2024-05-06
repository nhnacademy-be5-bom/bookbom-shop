package shop.bookbom.shop.common;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartItemDto;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.dto.response.MemberInfoResponse;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.entity.MemberStatus;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.order.entity.Order;
import shop.bookbom.shop.domain.orderstatus.entity.OrderStatus;
import shop.bookbom.shop.domain.pointhistory.dto.response.PointHistoryResponse;
import shop.bookbom.shop.domain.pointhistory.entity.ChangeReason;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistory;
import shop.bookbom.shop.domain.pointhistory.entity.PointHistoryDetail;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.wish.entity.Wish;

public class TestUtils {
    private TestUtils() {

    }


    public static Book getBook(String title, PointRate pointRate, Publisher publisher) {
        return Book.builder()
                .title(title)
                .description("description")
                .index("index")
                .pubDate(LocalDate.now())
                .isbn10("isbn10")
                .isbn13("isbn13")
                .cost(10000)
                .packagable(true)
                .views(0L)
                .status(BookStatus.FOR_SALE)
                .stock(100)
                .pointRate(pointRate)
                .publisher(publisher)
                .discountCost(8000)
                .build();
    }

    public static Cart getCart(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }

    public static Member getMember(String email, Role role, Rank rank) {
        return Member.builder()
                .email(email)
                .password("qwerqwreqwer")
                .name("test")
                .nickname("test")
                .phoneNumber("010-1234-1234")
                .birthDate(LocalDate.now())
                .registered(true)
                .role(role)
                .point(1000)
                .status(MemberStatus.ACTIVE)
                .rank(rank)
                .build();
    }

    public static Rank getRank(PointRate pointRate) {
        return Rank.builder()
                .name("test")
                .pointRate(pointRate)
                .build();
    }

    public static PointRate getPointRate() {
        return PointRate.builder()
                .name("test")
                .earnPoint(1)
                .earnType(EarnPointType.COST)
                .applyType(ApplyPointType.BOOK)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static List<CartAddRequest> getCartAddRequest() {
        List<CartAddRequest> requests = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartAddRequest cartAddRequest = new CartAddRequest((long) i, i);
            requests.add(cartAddRequest);
        }
        return requests;
    }

    public static CartInfoResponse getCartInfoResponse() {
        List<CartItemDto> cartItems = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartItemDto item = CartItemDto.builder()
                    .id((long) i)
                    .bookId((long) i)
                    .quantity(i)
                    .build();
            cartItems.add(item);
        }

        return CartInfoResponse.builder()
                .cartId(1L)
                .cartItems(cartItems)
                .build();
    }

    public static CartUpdateRequest getCartUpdateRequest() {
        CartUpdateRequest request = new CartUpdateRequest();
        request.setQuantity(5);
        return request;
    }

    public static CartUpdateResponse getCartUpdateResponse() {
        return CartUpdateResponse.builder()
                .quantity(10)
                .build();
    }

    public static Role getRole() {
        return Role.builder()
                .name("USER")
                .build();
    }

    public static Publisher getPublisher() {
        return Publisher.builder()
                .name("test")
                .build();
    }

    public static Order getOrder(User user, OrderStatus orderStatus, LocalDateTime orderDate) {
        return Order.builder()
                .orderNumber("orderNumber")
                .orderInfo("orderInfo")
                .orderDate(orderDate)
                .senderName("senderName")
                .senderPhoneNumber("senderPhoneNumber")
                .totalCost(10000)
                .discountCost(10000)
                .usedPoint(0)
                .user(user)
                .status(orderStatus)
                .build();
    }

    public static OrderStatus getOrderStatus() {
        return OrderStatus.builder()
                .name("test")
                .build();
    }

    public static Wish getWish(Member member, Book book) {
        return Wish.builder()
                .member(member)
                .book(book)
                .build();
    }

    public static MemberInfoResponse getMemberInfoResponse(Rank rank, Member member) {
        return MemberInfoResponse.builder()
                .rank(rank.getName())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .wishCount(2)
                .couponCount(0)
                .build();
    }

    public static User getUser(String email, String password, Role role) {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public static OrderInfoResponse getOrderInfoResponse(Order order) {
        return OrderInfoResponse.of(order);
    }

    public static PointHistory getPointHistory(Member member, ChangeReason changeReason) {
        return PointHistory.builder()
                .member(member)
                .changePoint(100)
                .changeReason(changeReason)
                .detail(PointHistoryDetail.ORDER_EARN)
                .changeDate(LocalDateTime.now())
                .build();
    }

    public static PointHistoryResponse getPointHistoryResponse(ChangeReason changeReason) {
        return PointHistoryResponse.builder()
                .id(1L)
                .reason(changeReason.name())
                .changeDate(LocalDateTime.now())
                .detail(PointHistoryDetail.ORDER_EARN.name())
                .changePoint(1000)
                .build();
    }
}
