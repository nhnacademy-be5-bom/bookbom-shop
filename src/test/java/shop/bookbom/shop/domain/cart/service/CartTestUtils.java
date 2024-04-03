package shop.bookbom.shop.domain.cart.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartInfoResponse;
import shop.bookbom.shop.domain.cart.dto.repsonse.CartUpdateResponse;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.dto.request.CartUpdateRequest;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.role.entity.Role;

public class CartTestUtils {
    private CartTestUtils() {

    }


    public static Book getBook(Long id, String title) {
        Book book = Book.builder()
                .title(title)
                .description("description")
                .index("index")
                .pubDate(LocalDate.now())
                .isbn10("isbn10")
                .build();
        ReflectionTestUtils.setField(book, "id", id);
        return book;
    }

    public static Cart getCart(Member member, Long id) {
        Cart cart = Cart.builder()
                .member(member)
                .build();
        ReflectionTestUtils.setField(cart, "id", id);
        return cart;
    }

    public static Member getMember(Long id, String email) {
        Member member = Member.builder()
                .email(email)
                .password("qwerqwreqwer")
                .name("test")
                .role(Role.builder().name("ROLE_ADMIN").build())
                .build();
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    public static List<CartAddRequest> getCartAddRequest() {
        List<CartAddRequest> requests = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartAddRequest cartAddRequest = new CartAddRequest();
            cartAddRequest.setBookId((long) i);
            cartAddRequest.setQuantity(i);
            requests.add(cartAddRequest);
        }
        return requests;
    }

    public static CartInfoResponse getCartInfoResponse() {
        List<CartInfoResponse.CartItemInfo> cartItems = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            CartInfoResponse.CartItemInfo cartItemInfo = CartInfoResponse.CartItemInfo.builder()
                    .bookId((long) i)
                    .quantity(i)
                    .build();
            cartItems.add(cartItemInfo);
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
}
