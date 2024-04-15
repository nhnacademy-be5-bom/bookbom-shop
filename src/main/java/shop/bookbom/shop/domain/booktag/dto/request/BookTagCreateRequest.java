package shop.bookbom.shop.domain.booktag.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookTagCreateRequest {
    @NotNull
    @Min(value = 1)
    private Long bookId;
    @NotNull
    @Min(value = 1)
    private Long tagId;
}
