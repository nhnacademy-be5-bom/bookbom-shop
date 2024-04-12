package shop.bookbom.shop.domain.booktag.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTagResponse {
    //get요청을 통해 tag의 이름을 넘겨주는 dto
    private String tagName;
}
