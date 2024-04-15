package shop.bookbom.shop.domain.booktag.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookTagInfoResponse {
    //get요청을 통해 tag의 이름을 넘겨주는 dto
    private String tagName;
    @Builder
    public BookTagInfoResponse(String tagName) {
        this.tagName = tagName;
    }
}
