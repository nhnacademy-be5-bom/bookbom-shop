package shop.bookbom.shop.domain.cart.service;


import java.time.LocalDate;
import java.util.List;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.cart.dto.request.CartAddRequest;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.role.entity.Role;

public class CartTestUtils {
    private CartTestUtils() {

    }


    public static Book getBook() {
        return Book.builder()
                .title("bookTitle")
                .description("description")
                .index("index")
                .pubDate(LocalDate.now())
                .isbn10("isbn10")
                .build();
    }

    public static Cart getCart(Member member) {
        return Cart.builder()
                .member(member)
                .build();
    }

    public static Member getMember() {
        return Member.builder()
                .id(1L)
                .email("test@naver.com")
                .password("qwerqwreqwer")
                .name("test")
                .role(Role.builder().name("ROLE_ADMIN").build())
                .build();
    }

    public static List<CartAddRequest> getCartAddRequest() {
        CartAddRequest cartAddRequest = new CartAddRequest(1L, 2);
        return List.of(cartAddRequest);
    }
}
