package shop.bookbom.shop.domain.book.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.common.file.ObjectService;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.author.dto.AuthorSimpleInfo;
import shop.bookbom.shop.domain.author.entity.Author;
import shop.bookbom.shop.domain.author.exception.AuthorIdNotFoundException;
import shop.bookbom.shop.domain.author.repository.AuthorRepository;
import shop.bookbom.shop.domain.book.dto.BookSearchResponse;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.exception.BookNotFoundException;
import shop.bookbom.shop.domain.book.exception.IdMismatchException;
import shop.bookbom.shop.domain.book.repository.BookRepository;
import shop.bookbom.shop.domain.bookauthor.entity.BookAuthor;
import shop.bookbom.shop.domain.bookauthor.repository.BookAuthorRepository;
import shop.bookbom.shop.domain.bookcategory.entity.BookCategory;
import shop.bookbom.shop.domain.bookcategory.repository.BookCategoryRepository;
import shop.bookbom.shop.domain.bookfile.entity.BookFile;
import shop.bookbom.shop.domain.bookfile.repository.BookFileRepository;
import shop.bookbom.shop.domain.bookfiletype.repository.BookFileTypeRepository;
import shop.bookbom.shop.domain.booktag.entity.BookTag;
import shop.bookbom.shop.domain.booktag.repository.BookTagRepository;
import shop.bookbom.shop.domain.category.entity.Status;
import shop.bookbom.shop.domain.category.exception.NoSuchCategoryNameException;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;
import shop.bookbom.shop.domain.file.entity.File;
import shop.bookbom.shop.domain.file.exception.ThumbNailNotFoundException;
import shop.bookbom.shop.domain.file.repository.FileRepository;
import shop.bookbom.shop.domain.pointrate.entity.PointRate;
import shop.bookbom.shop.domain.pointrate.repository.PointRateRepository;
import shop.bookbom.shop.domain.publisher.entity.Publisher;
import shop.bookbom.shop.domain.publisher.repository.PublisherRepository;
import shop.bookbom.shop.domain.review.repository.ReviewRepository;
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
    private final FileRepository fileRepository;
    private final BookFileTypeRepository bookFileTypeRepository;
    private final BookFileRepository bookFileRepository;
    private final ReviewRepository reviewRepository;
    private final ObjectService objectService;
    private static final String CONTAINER_NAME = "bookbom/book_thumbnail";

    @Transactional(readOnly = true)
    public BookDetailResponse getBookDetailInformation(Long bookId) {
        return bookRepository.getBookDetailInfoById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public BookMediumResponse getBookMediumInformation(Long bookId) {
        return bookRepository.getBookMediumInfoById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public BookSimpleResponse getBookSimpleInformation(Long bookId) {
        return bookRepository.getBookSimpleInfoById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<BookSearchResponse> getPageableEntireBookList(Pageable pageable) {

        return bookRepository.getPageableListBookMediumInfos(pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookSearchResponse> getPageableEntireBookListOrderByCount(Pageable pageable) {

        return bookRepository.getPageableAndOrderByViewCountListBookMediumInfos(pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookSearchResponse> getPageableBookListByCategoryId(Long categoryId,
                                                                    String sortCondition,
                                                                    Pageable pageable) {

        return bookRepository.getPageableBookMediumInfosByCategoryId(categoryId, sortCondition, pageable);
    }

    @Transactional
    @Modifying
    public void addBook(BookAddRequest bookAddRequest) {
        // 출판사 저장
        Publisher publisher = handleNewPublisher(bookAddRequest.getPublisher());
        // 포인트 적립율 저장: 도서 기본 적립율
        PointRate pointRate = pointRateRepository.getReferenceById(1L);
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

        // 태그, 책-태그 저장
        handleNewTag(bookAddRequest.getTags(), book);
        // 작가, 책-작가 저장
        handleNewAuthor(bookAddRequest.getAuthors(), book);
        // 책-카테고리 저장, 카테고리는 저장하지 않음: 카테고리 저장 페이지에서만 가능
        handleNewCategory(bookAddRequest.getCategories(), book);
        // 파일, 책-파일 저장
        handleThumbnail(bookAddRequest.getThumbnail(), book);
    }

    @Transactional
    @Modifying
    public void updateBook(BookUpdateRequest bookUpdateRequest, Long bookId) {

        if (Objects.equals(bookUpdateRequest.getBookId(), bookId)) {
            Book targetBook = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
            targetBook.update(bookUpdateRequest);

            //출판사 업데이트
            Publisher targetPublisher = targetBook.getPublisher();
            updatePublisher(targetBook.getPublisher(), bookUpdateRequest.getPublisher());
            targetBook.updatePublisher(targetPublisher);

            bookRepository.save(targetBook);

            // 태그 업데이트
            resetBookTag(targetBook.getTags());
            handleNewTag(bookUpdateRequest.getTags(), targetBook);
            // 작가 업데이트
            updateAuthor(bookUpdateRequest.getAuthors());
            // 카테고리 업데이트
            resetBookCategory(targetBook.getCategories());
            handleNewCategory(bookUpdateRequest.getCategories(), targetBook);
            // 썸네일 업데이트
            File thumbnail = fileRepository.findThumbnailByBookId(bookId).orElseThrow(ThumbNailNotFoundException::new);
            updateThumbnail(bookUpdateRequest.getThumbnail(), thumbnail);

        } else {
            throw new IdMismatchException();
        }

    }

    @Transactional
    @Modifying
    public void updateBookViewCount(Long bookId, Long hits) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookToUpdate.updateViewCount(hits);
        bookRepository.save(bookToUpdate);
    }

    @Transactional
    @Modifying
    public void updateBookStock(Long bookId, Integer newStock) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookToUpdate.updateStock(newStock);
        bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        Book bookToDelete = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookToDelete.updateStatus(BookStatus.DEL);
        bookRepository.save(bookToDelete);
    }

    @Transactional
    @Modifying
    public void reviveBook(Long bookId) {
        Book bookToRevive = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookToRevive.updateStatus(BookStatus.FS);
        bookRepository.save(bookToRevive);
    }

    private Publisher handleNewPublisher(String publisherName) {
        Publisher publisherEntity = Publisher.builder()
                .name(publisherName)
                .build();

        publisherRepository.save(publisherEntity);

        return publisherEntity;
    }

    private void handleNewAuthor(List<AuthorSimpleInfo> authors, Book book) {

        for (AuthorSimpleInfo author : authors) {
            Author authorEntity = Author.builder()
                    .name(author.getName())
                    .build();
            authorRepository.save(authorEntity);

            BookAuthor bookAuthor = BookAuthor.builder()
                    .book(book)
                    .role(author.getRole())
                    .author(authorEntity)
                    .build();
            bookAuthorRepository.save(bookAuthor);
        }
    }

    private void handleNewCategory(List<String> categoryNames, Book book) {
        if (categoryNames == null) {
            BookCategory bookCategory = BookCategory.builder()
                    .book(book)
                    //카테고리 777= "카테고리 없음"
                    .category(categoryRepository.findById(777L).orElseThrow())
                    .build();
            bookCategoryRepository.save(bookCategory);
        } else {
            for (String categoryName : categoryNames) {

                BookCategory bookCategory = BookCategory.builder()
                        .book(book)
                        .category(categoryRepository.findByName(categoryName)
                                .orElseThrow(NoSuchCategoryNameException::new))
                        .build();
                bookCategoryRepository.save(bookCategory);
            }
        }
    }

    private void handleNewTag(List<String> tags, Book book) {

        for (String tagName : tags) {
            // 존재하지 않는 새 태그만 저장
            if (!tagRepository.existsByName(tagName)) {
                Tag tagEntity = Tag.builder()
                        .name(tagName)
                        .status(Status.USED)
                        .build();
                tagRepository.save(tagEntity);

                BookTag bookTag = BookTag.builder()
                        .book(book)
                        .tag(tagEntity)
                        .build();
                booktagRepository.save(bookTag);
            }
        }
    }

    private void updateAuthor(List<AuthorDTO> authorUpdateInfo) {

        for (AuthorDTO authorInfo : authorUpdateInfo) {

            Optional<Author> target = authorRepository.findById(authorInfo.getId());

            if (target.isPresent() && !Objects.equals(target.get().getId(), authorInfo.getId())) {
                target.get().update(authorInfo.getName());
                authorRepository.save(target.get());

            } else {
                throw new AuthorIdNotFoundException();
            }
        }
    }

    private void updatePublisher(Publisher target, String newName) {
        target.update(newName);
        publisherRepository.save(target);
    }

    private void updateCategory(List<String> categoryNames, Book book) {
        if (categoryNames == null) {
            BookCategory bookCategory = BookCategory.builder()
                    .book(book)
                    .category(categoryRepository.findById(777L).orElseThrow())//카테고리 없음
                    .build();
            bookCategoryRepository.save(bookCategory);
        } else {
            for (String categoryName : categoryNames) {

                BookCategory bookCategory = BookCategory.builder()
                        .book(book)
                        .category(categoryRepository.findByName(categoryName)
                                .orElseThrow(NoSuchCategoryNameException::new))
                        .build();
                bookCategoryRepository.save(bookCategory);
            }
        }
    }

    private void resetBookTag(List<BookTag> bookTagList) {
        booktagRepository.deleteAll(bookTagList);
    }

    private void resetBookCategory(List<BookCategory> bookCategoryList) {
        bookCategoryRepository.deleteAll(bookCategoryList);
    }

    private void handleThumbnail(MultipartFile thumbnail, Book book) {
        String objectName = book.getTitle().length() > 7 ? (book.getTitle().substring(1, 7) + "_thumbnail") :
                (book.getTitle().substring(1, book.getTitle().length() - 1) + "_thumbnail");

        objectService.uploadFile(thumbnail, CONTAINER_NAME, objectName);

        File file = File.builder()
                .url(objectService.getUrl(CONTAINER_NAME, objectName))
                .extension(StringUtils.getFilenameExtension(thumbnail.getOriginalFilename()))
                .createdAt(LocalDateTime.now())
                .build();
        fileRepository.save(file);

        BookFile bookFile = BookFile.builder()
                .book(book)
                .bookFileType(bookFileTypeRepository.getReferenceById(1L))//= "img"
                .file(file)
                .build();
        bookFileRepository.save(bookFile);
    }

    private void updateThumbnail(MultipartFile newThumbnail, File originalThumbnail) {
        String url = originalThumbnail.getUrl();
        int index = url.indexOf(CONTAINER_NAME) + CONTAINER_NAME.length();
        String objectName = url.substring(index);

        objectService.uploadFile(newThumbnail, CONTAINER_NAME, objectName);

        originalThumbnail.update(objectService.getUrl(CONTAINER_NAME, objectName),
                StringUtils.getFilenameExtension(newThumbnail.getOriginalFilename()),
                LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public long getReviewCount(Long bookId) {
        return reviewRepository.countByBookId(bookId)
                .orElse(0L);
    }

    @Transactional(readOnly = true)
    public double getReviewRating(Long bookId) {
        double avgRate = reviewRepository.avgRateByBookId(bookId)
                .orElse(0.0);
        return Math.round(avgRate * 10) / 10.0;
    }
}
