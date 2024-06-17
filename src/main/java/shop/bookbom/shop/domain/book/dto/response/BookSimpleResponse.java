package shop.bookbom.shop.domain.book.dto.response;

import static shop.bookbom.shop.domain.book.DtoToListHandler.processFiles;

import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.file.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;


@Getter
@NoArgsConstructor
public class BookSimpleResponse {
    // 주문, 장바구니에 사용하는 최소 정보 DTO
    // id, 표지, 제목, 가격, 할인가격, 적립율
    private Long id;
    private String title;
    private Integer cost;
    private Integer discountCost;
    private PointRateSimpleInformation pointRate;
    private List<FileDTO> files = new ArrayList<>();

    @Builder
    @QueryProjection
    public BookSimpleResponse(Long id,
                              String title,
                              Integer cost,
                              Integer discountCost,
                              PointRateSimpleInformation pointRate,
                              List<FileDTO> files) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.discountCost = discountCost;
        this.pointRate = pointRate;
        this.files = files;
    }

    public static BookSimpleResponse of(Book book,
                                        List<BookFile> files) {
        return BookSimpleResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .cost(book.getCost())
                .discountCost(book.getDiscountCost())
                .pointRate(PointRateSimpleInformation.builder()
                        .earnType(book.getPointRate().getEarnType().getValue())
                        .earnPoint(book.getPointRate().getEarnPoint())
                        .build()
                )
                .files(processFiles(files))
                .build();
    }
}
