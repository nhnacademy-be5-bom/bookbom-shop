package shop.bookbom.shop.domain.booktag.dto.response;

import lombok.Builder;
import lombok.Getter;
import shop.bookbom.shop.domain.category.entity.Status;

@Getter
public class BookTagInfoResponse {
    //get요청을 통해 tag의 정보를 넘겨주는 dto
    private Long tagId;
    private String tagName;
    private Status status;
    @Builder
    public BookTagInfoResponse(String tagName, Long tagId, Status status) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.status = status;
    }
}
