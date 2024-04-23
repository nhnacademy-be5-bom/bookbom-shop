package shop.bookbom.shop.domain.cart.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.bookbom.shop.common.TestUtils.getBook;
import static shop.bookbom.shop.common.TestUtils.getCart;
import static shop.bookbom.shop.common.TestUtils.getMember;
import static shop.bookbom.shop.common.TestUtils.getPointRate;
import static shop.bookbom.shop.common.TestUtils.getPublisher;
import static shop.bookbom.shop.common.TestUtils.getRank;
import static shop.bookbom.shop.common.TestUtils.getRole;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.bookbom.shop.config.TestQuerydslConfig;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.cart.entity.Cart;
import shop.bookbom.shop.domain.cart.repository.CartRepository;
import shop.bookbom.shop.domain.cartitem.entity.CartItem;
import shop.bookbom.shop.domain.cartitem.repository.CartItemRepository;
import shop.bookbom.shop.domain.member.entity.Member;
import shop.bookbom.shop.domain.member.repository.MemberRepository;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.publisher.repository.PublisherRepository;
import shop.bookbom.shop.domain.rank.entity.Rank;
import shop.bookbom.shop.domain.rank.repository.RankRepository;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;

@Import(TestQuerydslConfig.class)
@DataJpaTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    PointRateRepository pointRateRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 조회")
    void getCartWithCartItems() {
        // given
        Role role = roleRepository.save(getRole());
        PointRate pointrate = pointRateRepository.save(getPointRate());
        Rank rank = rankRepository.save(getRank(pointrate));
        Member member = memberRepository.save(getMember("test@email.com", role, rank));
        Publisher publisher = publisherRepository.save(getPublisher());
        Book book1 = bookRepository.save(getBook("title1", pointrate, publisher));
        Book book2 = bookRepository.save(getBook("title2", pointrate, publisher));
        Cart cart = getCart(member);
        cartRepository.save(cart);
        CartItem item1 = cartItemRepository.save(CartItem.builder()
                .cart(cart)
                .book(book1)
                .build());
        CartItem item2 = cartItemRepository.save(CartItem.builder()
                .cart(cart)
                .book(book2)
                .build());
        cart.addItem(item1);
        cart.addItem(item2);
        // when
        Cart result = cartRepository.getCartFetchItems(member).get();
        List<CartItem> cartItems = result.getCartItems();
        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(cartItems.size()).isEqualTo(2);
        assertThat(cartItems.get(0).getBook().getTitle()).isEqualTo("title1");
        assertThat(cartItems.get(1).getBook().getTitle()).isEqualTo("title2");
    }
}
