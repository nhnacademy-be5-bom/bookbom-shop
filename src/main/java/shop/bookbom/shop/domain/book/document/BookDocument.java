package shop.bookbom.shop.domain.book.document;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bom_dev-book")
public class BookDocument {

    @Id
    @Field(name = "book_id", type = FieldType.Long)
    private Long bookId;

    @Field(name = "book_title", type = FieldType.Text)
    private String bookTitle;

    @Field(type = FieldType.Text)
    private String description;

    @Field(name = "book_index", type = FieldType.Text)
    private String bookIndex;

    @Field(type = FieldType.Long)
    private Long cost;

    @Field(name = "discount_cost", type = FieldType.Long)
    private Long discountCost;

    @Field(name = "author_ids", type = FieldType.Text)
    private String authorIds;

    @Field(name = "author_names", type = FieldType.Text)
    private String authorNames;

    @Field(name = "publisher_id", type = FieldType.Long)
    private Long publisherId;

    @Field(name = "publisher_name", type = FieldType.Text)
    private String publisherName;
}
