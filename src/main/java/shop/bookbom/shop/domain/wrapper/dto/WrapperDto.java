package shop.bookbom.shop.domain.wrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrapperDto {
    private Long id;
    private String name;
    private int cost;
}
