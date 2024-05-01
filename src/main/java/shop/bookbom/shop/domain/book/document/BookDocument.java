package shop.bookbom.shop.domain.book.document;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "bom_dev-book")
public class BookDocument {

    @Id
    @Field(name = "book_id", type = FieldType.Long)
    private Long bookId;

    @Field(name = "title", type = FieldType.Text)
    private String bookTitle;

    @Field(type = FieldType.Text)
    private String description;

    @Field(name = "book_index", type = FieldType.Text)
    private String bookIndex;

    @Field(type = FieldType.Integer)
    private Integer cost;

    @Field(name = "discount_cost", type = FieldType.Integer)
    private Integer discountCost;

    @Field(name = "pub_date", type = FieldType.Date)
    private LocalDate pubDate;

    @Field(name = "author_ids", type = FieldType.Text)
    private String authorIds;

    @Field(name = "author_roles", type = FieldType.Text)
    private String authorRoles;

    @Field(name = "author_names", type = FieldType.Text)
    private String authorNames;

    @Field(name = "publisher_id", type = FieldType.Long)
    private Long publisherId;

    @Field(name = "publisher_name", type = FieldType.Text)
    private String publisherName;

    @Field(name = "views", type = FieldType.Integer)
    private Integer views;

    @Field(name = "file_id", type = FieldType.Long)
    private Long fileId;

    @Field(name = "url", type = FieldType.Text)
    private String url;

    @Field(name = "extension", type = FieldType.Text)
    private String extension;
}
