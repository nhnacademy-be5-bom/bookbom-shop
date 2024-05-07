package shop.bookbom.shop.domain.order.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.file.entity.File;
import shop.bookbom.shop.domain.orderbook.entity.OrderBook;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderBookResponse {
    private Long id;
    private String thumbnail;
    private String title;
    private int bookPrice;
    private int quantity;
    private boolean isPackaging;
    private int wrapperCost;
    private String status;

    public static OrderBookResponse of(OrderBook orderBook) {
        String thumbnail = orderBook.getBook().getBookFiles().stream()
                .filter(BookFile::isThumbnail)
                .map(BookFile::getFile)
                .map(File::getUrl)
                .findFirst()
                .orElse(null);

        return new OrderBookResponse(
                orderBook.getBook().getId(),
                thumbnail,
                orderBook.getBook().getTitle(),
                orderBook.getBookPrice(),
                orderBook.getQuantity(),
                orderBook.isPackaging(),
                orderBook.getWrapper().getCost(),
                orderBook.getStatus().name()
        );
    }
}
