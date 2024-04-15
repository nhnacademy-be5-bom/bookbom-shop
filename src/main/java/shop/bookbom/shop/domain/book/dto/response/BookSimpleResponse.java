package shop.bookbom.shop.domain.book.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.bookbom.shop.domain.file.entity.dto.FileDTO;
import shop.bookbom.shop.domain.pointrate.dto.PointRateSimpleInformation;


@Getter
@NoArgsConstructor
public class BookSimpleResponse {
    // 주문, 장바구니에 사용하는 최소 정보 DTO
    // 표지, 제목, 가격, 할인가격, 적립율
    private String title;
    private Integer cost;
    private Integer discountCost;
    private PointRateSimpleInformation pointRate;
    private List<FileDTO> files = new ArrayList<>();

    @Builder
    @QueryProjection
    public BookSimpleResponse(String title,
                              Integer cost,
                              Integer discountCost,
                              PointRateSimpleInformation pointRate,
                              List<FileDTO> files) {

        this.title = title;
        this.cost = cost;
        this.discountCost = discountCost;
        this.pointRate = pointRate;
        this.files = files;
    }
}
