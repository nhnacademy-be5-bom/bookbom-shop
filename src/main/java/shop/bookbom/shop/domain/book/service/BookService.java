package shop.bookbom.shop.domain.book.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.bookbom.shop.domain.author.dto.AuthorDTO;
import shop.bookbom.shop.domain.author.dto.AuthorSimpleInfo;
import shop.bookbom.shop.domain.author.entity.Author;
import shop.bookbom.shop.domain.author.exception.AuthorIdNotFoundException;
import shop.bookbom.shop.domain.author.repository.AuthorRepository;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.dto.response.BookDetailResponse;
import shop.bookbom.shop.domain.book.dto.response.BookMediumResponse;
import shop.bookbom.shop.domain.book.dto.response.BookSimpleResponse;
import shop.bookbom.shop.domain.book.entity.Book;
import shop.bookbom.shop.domain.book.entity.BookStatus;
import shop.bookbom.shop.domain.book.exception.BookIdMismatchException;
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
import shop.bookbom.shop.domain.category.exception.NoSuchCategoryNameException;
import shop.bookbom.shop.domain.category.repository.CategoryRepository;
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
    //private final FileRepository fileRepository;
    private final BookFileRepository bookFileRepository;

    private final ObjectMapper mapper;

    @Transactional(readOnly = true)
    public BookDetailResponse getBookDetailInformation(Long bookId) {
        if (exists(bookId)) {
            return bookRepository.getBookDetailInfoById(bookId);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public BookMediumResponse getBookMediumInformation(Long bookId) {
        if (exists(bookId)) {
            return bookRepository.getBookMediumInfoById(bookId);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public BookSimpleResponse getBookSimpleInformation(Long bookId) {
        if (exists(bookId)) {
            return bookRepository.getBookSimpleInfoById(bookId);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public Page<BookMediumResponse> getPageableEntireBookList(Pageable pageable) {

        return bookRepository.getPageableListBookMediumInfos(pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookMediumResponse> getPageableEntireBookListOrderByCount(Pageable pageable) {

        return bookRepository.getPageableAndOrderByViewCountListBookMediumInfos(pageable);
    }

    @Transactional(readOnly = true)
    public Page<BookMediumResponse> getPageableBookListByCategoryId(Long categoryId, Pageable pageable) {

        return bookRepository.getPageableBookMediumInfosByCategoryId(categoryId, pageable);
    }

    @Transactional
    public void putBook(BookAddRequest bookAddRequest) {
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
        // #todo 파일,  책-파일 저장
        handleThumbnail(bookAddRequest.getThumbnail(), book);
    }

    @Transactional
    public void updateBook(BookUpdateRequest bookUpdateRequest, Long bookId) {
        if (exists(bookId)) {
            if (Objects.equals(bookUpdateRequest.getBookId(), bookId)) {
                Book targetBook = bookRepository.findByIdFetch(bookId);

                Book updatedBook = Book.updateBuilder()
                        .id(bookId)
                        .title(bookUpdateRequest.getTitle())
                        .description(bookUpdateRequest.getDescription())
                        .index(bookUpdateRequest.getIndex())
                        .pubDate(bookUpdateRequest.getPubDate())
                        .isbn10(bookUpdateRequest.getIsbn10())
                        .isbn13(bookUpdateRequest.getIsbn13())
                        .cost(bookUpdateRequest.getCost())
                        .discountCost(bookUpdateRequest.getDiscountCost())
                        .packagable(bookUpdateRequest.getPackagable())
                        .views(targetBook.getViews())
                        .status(bookUpdateRequest.getStatus())
                        .stock(bookUpdateRequest.getStock())
                        //출판사 업데이트
                        .publisher(updatePublisher(targetBook.getPublisher(), bookUpdateRequest.getPublisher()))
                        .pointRate(targetBook.getPointRate())
                        .build();

                bookRepository.save(updatedBook);

                // 태그 업데이트
                resetBookTag(targetBook.getTags());
                handleNewTag(bookUpdateRequest.getTags(), updatedBook);
                // 작가 업데이트
                updateAuthor(bookUpdateRequest.getAuthors());
                // 카테고리 업데이트
                resetBookCategory(targetBook.getCategories());
                handleNewCategory(bookUpdateRequest.getCategories(), updatedBook);

            } else {
                throw new BookIdMismatchException();
            }
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional
    public void updateBookViewCount(Long bookId, Long hits) {
        if (exists(bookId)) {
            Book bookToUpdate = bookRepository.findById(bookId).get();
            bookToUpdate.updateViewCount(hits);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional
    public void updateBookStock(Long bookId, Integer newStock) {
        if (exists(bookId)) {
            Book bookToUpdate = bookRepository.findById(bookId).get();
            bookToUpdate.updateStock(newStock);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional
    public void deleteBook(Long bookId) {
        if (exists(bookId)) {
            Book bookToDelete = bookRepository.findById(bookId).get();
            bookToDelete.updateStatus(BookStatus.DEL);
        } else {
            throw new BookNotFoundException();
        }
    }

    @Transactional
    public void reviveBook(Long bookId) {
        if (exists(bookId)) {
            Book bookToDelete = bookRepository.findById(bookId).get();
            bookToDelete.updateStatus(BookStatus.FS);
        } else {
            throw new BookNotFoundException();
        }
    }


    private boolean exists(Long bookId) {
        try {
            bookRepository.getReferenceById(bookId);
            return true;

        } catch (RuntimeException e) {
            return false;
        }
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

                Author updateEntity = Author.updateBuilder()
                        .id(authorInfo.getId())
                        .name(authorInfo.getName())
                        .build();

                authorRepository.save(updateEntity);
            } else {
                throw new AuthorIdNotFoundException();
            }
        }
    }

    private Publisher updatePublisher(Publisher target, String newName) {
        Publisher publisherEntity = Publisher.updateBuilder()
                .id(target.getId())
                .name(newName)
                .build();

        publisherRepository.save(publisherEntity);
        return publisherEntity;
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
        //# todo 썸네일 처리
    }
}
