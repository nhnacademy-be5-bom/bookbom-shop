package shop.bookbom.shop.domain.wrapper.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WrapperResponse {
    private Long id;
    private String name;
    private int cost;

    @Builder

    public WrapperResponse(Long id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
}
