package shop.bookbom.shop.domain.cart.dto.repsonse;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;

@Getter
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String thumbnail;
    private String title;
    private int price;
    private int discountPrice;
    private int quantity;

    @Builder
    public CartItemDto(Long id, Long bookId, String thumbnail, String title, int price, int discountPrice,
                       int quantity) {
        this.id = id;
        this.bookId = bookId;
        this.thumbnail = thumbnail;
        this.title = title;
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
    }

    public static CartItemDto from(CartItem cartItem, String thumbnail) {
        Book book = cartItem.getBook();
        return CartItemDto.builder()
                .id(cartItem.getId())
                .bookId(book.getId())
                .thumbnail(thumbnail)
                .title(book.getTitle())
                .price(book.getCost())
                .discountPrice(book.getDiscountCost())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
