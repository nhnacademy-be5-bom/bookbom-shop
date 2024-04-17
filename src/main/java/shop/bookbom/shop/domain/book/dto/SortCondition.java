package shop.bookbom.shop.domain.book.dto;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortCondition {
    NAME("title", Sort.Direction.DESC),
    LATEST("pubDate", Sort.Direction.DESC),
    LOWEST_PRICE("cost", Sort.Direction.ASC),
    HIGHEST_PRICE("cost", Sort.Direction.DESC),
    POPULAR("views", Sort.Direction.DESC),
    NONE("", null);

    private final String fieldName;
    private final Sort.Direction direction;

    SortCondition(String fieldName, Sort.Direction direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }
}
