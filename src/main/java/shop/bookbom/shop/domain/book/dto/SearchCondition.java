package shop.bookbom.shop.domain.book.dto;

import lombok.Getter;

@Getter
public enum SearchCondition {
    BOOK_TITLE("title"),
    AUTHOR("author_names"),
    PUBLISHER("publisher_name"),
    NONE("");

    private final String fieldName;

    SearchCondition(String fieldName) {
        this.fieldName = fieldName;
    }
}
