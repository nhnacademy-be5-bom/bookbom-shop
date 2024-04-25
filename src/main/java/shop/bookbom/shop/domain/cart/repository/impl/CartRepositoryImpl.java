package shop.bookbom.shop.domain.cart.repository.impl;


import static shop.bookbom.shop.domain.book.entity.QBook.book;
import static shop.bookbom.shop.domain.cart.entity.QCart.cart;
import static shop.bookbom.shop.domain.cartitem.entity.QCartItem.cartItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepositoryCustom;
import shop.bookbom.shop.domain.member.entity.Member;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Cart> getCartFetchItems(Member member) {
        Cart result = queryFactory.selectFrom(cart)
                .leftJoin(cart.cartItems, cartItem).fetchJoin()
                .join(cartItem.book, book).fetchJoin()
                .where(cart.member.eq(member))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
