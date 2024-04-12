package shop.bookbom.shop.domain.booktag.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BookTagRequest {
    @NotNull
    @Min(value = 1)
    private Long bookId;
    @NotNull
    @Min(value = 1)
    private Long tagId;
}
