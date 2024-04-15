package shop.bookbom.shop.domain.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.author.entity.Author;
import shop.bookbom.shop.domain.author.repository.AuthorRepository;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.repository.BookAuthorRepository;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookcategory.repository.BookCategoryRepository;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;
import shop.bookbom.shop.domain.pointrate.entity.ApplyPointType;
import shop.bookbom.shop.domain.pointrate.entity.EarnPointType;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.publisher.repository.PublisherRepository;
import shop.bookbom.shop.domain.tag.entity.Tag;
import shop.bookbom.shop.domain.tag.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    private final PointRateRepository pointRateRepository;

    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    private final TagRepository tagRepository;
    private final BookTagRepository booktagRepository;

    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    // private final FileRepository fileRepository;
    private final BookFileRepository bookFileRepository;

    private final ObjectMapper mapper;

    public boolean exists(Long bookId) {
        try {
            bookRepository.getReferenceById(bookId);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public BookDetailResponse getBookDetailInformation(Long bookId) {
        if (exists(bookId)) {
            Publisher publisher = bookRepository.findById(bookId).get().getPublisher();
            return bookRepository.getBookDetailById(bookId).get(0);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public BookDetailResponse getBookSimpleInformation(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        return mapper.convertValue(book, BookDetailResponse.class);
    }

    @Transactional
    public void putBook(BookAddRequest bookAddRequest) {
        // 출판사 저장
        Publisher publisher = bookAddRequest.getPublisher();
        publisherRepository.save(publisher);

        // 포인트 적립율 저장: 도서 기본 적립율
        PointRate pointRate = PointRate.builder()
                .name(bookAddRequest.getTitle())
                .earnType(EarnPointType.RATE)
                .earnPoint(2) // 기본 퍼센티지
                .applyType(ApplyPointType.BOOK)
                .createdAt(LocalDateTime.now())
                .build();
        pointRateRepository.save(pointRate);

        // 작가 저장
        Author author = Author.builder()
                .name(bookAddRequest.getAuthors())
                .build();
        authorRepository.save(author);

        // 카테고리는 저장하지 않음: 카테고리 저장 페이지에서만 가능

        // #todo 태그 저장
        List<String> requestTags = bookAddRequest.getTags();

        // #todo 파일 저장
        MultipartFile thumbnail = bookAddRequest.getThumbnail();

        //책 저장
        Book book = Book.builder()
                .title(bookAddRequest.getTitle())
                .description(bookAddRequest.getDescription())
                .index(bookAddRequest.getIndex())
                .pubDate(bookAddRequest.getPubDate())
                .isbn10(bookAddRequest.getIsbn10())
                .isbn13(bookAddRequest.getIsbn13())
                .cost(bookAddRequest.getCost())
                .discountCost(bookAddRequest.getDiscountCost())
                .packagable(bookAddRequest.getPackagable())
                .views(0L)
                .status(bookAddRequest.getStatus())
                .stock(bookAddRequest.getStock())
                .publisher(publisher)
                .pointRate(pointRate)
                .build();

        bookRepository.save(book);

        for (String tagName : requestTags) {
            // 존재하지 않는 새 태그만 저장
            if (!tagRepository.existsByName(tagName)) {
                Tag newTag = Tag.builder()
                        .name(tagName)
                        .status(Status.USED)
                        .build();
                tagRepository.save(newTag);

                // 책-태그 저장
                BookTag bookTag = BookTag.builder()
                        .book(book)
                        .tag(newTag)
                        .build();
                booktagRepository.save(bookTag);
            }
        }

        // 책-작가 저장
        BookAuthor bookAuthor = BookAuthor.builder()
                .role("작가")
                .book(book)
                .author(author)
                .build();
        bookAuthorRepository.save(bookAuthor);

        // 책-카테고리 저장
        if (bookAddRequest.getCategories() != null) {
            for (String categoryName : bookAddRequest.getCategories()) {
                BookCategory bookCategory = BookCategory.builder()
                        .book(book)
                        .category(categoryRepository.findByName(categoryName).get())
                        .build();
                bookCategoryRepository.save(bookCategory);
            }
        }


        // 책-파일 저장
    }

    @Transactional
    public void updateBook(BookUpdateRequest bookUpdateRequest) {
        bookRepository.save(mapper.convertValue(bookUpdateRequest, Book.class));
    }
}
