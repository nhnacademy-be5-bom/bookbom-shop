package shop.bookbom.shop.domain.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookAddRequest;
import static shop.bookbom.shop.domain.book.utils.BookTestUtils.getBookUpdateRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shop.bookbom.shop.argumentresolver.LoginArgumentResolver;
import shop.bookbom.shop.config.WebConfig;
import shop.bookbom.shop.domain.book.dto.request.BookAddRequest;
import shop.bookbom.shop.domain.book.dto.request.BookUpdateRequest;
import shop.bookbom.shop.domain.book.service.BookService;

/**
 * packageName    : shop.bookbom.shop.domain.book.controller
 * fileName       : AdminBookControllerTest
 * author         : UuLaptop
 * date           : 2024-04-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-17        UuLaptop       최초 생성
 */
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AdminBookController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {WebConfig.class, LoginArgumentResolver.class}))
class AdminBookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("책 등록")
    void addBook() throws Exception {
        BookAddRequest bookAddRequest = getBookAddRequest("테스트 책");

        byte[] fileContent = "testFile".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileContent);

        MockMultipartFile request = new MockMultipartFile("bookAddRequest", null, "application/json",
                mapper.writeValueAsString(bookAddRequest).getBytes(
                        StandardCharsets.UTF_8));

        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart(HttpMethod.PUT, "/shop/admin/books/update/new")
                        .file(mockMultipartFile)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        verify(bookService, times(1)).addBook(any(), any());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("책 수정")
    void updateBook() throws Exception {
        BookUpdateRequest bookUpdateRequest = getBookUpdateRequest("테스트 책");

        byte[] fileContent = "testFile".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileContent);

        MockMultipartFile request = new MockMultipartFile("bookUpdateRequest", null, "application/json",
                mapper.writeValueAsString(bookUpdateRequest).getBytes(
                        StandardCharsets.UTF_8));


        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart(HttpMethod.PUT, "/shop/admin/books/update/{id}", 1L)
                        .file(mockMultipartFile)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        verify(bookService, times(1)).updateBook(any(), any(), anyLong());

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }

    @Test
    @DisplayName("책 삭제: soft delete")
    void deleteBook() throws Exception {

        ResultActions perform = mockMvc.perform(delete("/shop/admin/books/delete/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        verify(bookService, times(1)).deleteBook(1L);

        perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.header.resultMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.header.successful").value(true));
    }
}
