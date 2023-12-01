package com.example.Database.controller;

import com.example.Database.TestDataUtil;
import com.example.Database.domain.dto.BookDto;
import com.example.Database.domain.entities.AuthorEntity;
import com.example.Database.domain.entities.BookEntity;
import com.example.Database.services.IBookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {
    private MockMvc mockMvc;
    private IBookService bookService;
    private ObjectMapper objectMapper;
    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc, IBookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);

        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullySavedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);

        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("458-1-2345-6789-2")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The shadow of programming")
        );
    }

    @Test
    public void testThatListBookSuccessfullyReturnsHttp200Ok() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

//    @Test
//    public void testThatListBookSuccessfullyReturnsBooks() throws Exception {
//        BookEntity bookDto = TestDataUtil.createTestBook(null);
//        bookService.createUpdateBook(bookDto.getIsbn(), bookDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$[0].title").value("The shadow in the Attic")
//        );
//    }

    @Test
    public void testThatSingleBookSuccessfullyReturnsHttp20Ok() throws Exception {
        BookEntity bookDto = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatSingleBookSuccessfullyReturnsHttpNotFound() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatSingleBookSuccessfullyReturnsBook() throws Exception {
        BookEntity bookDto = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(bookDto.getIsbn(), bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The shadow in the Attic")
        );
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsHttp20Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("Book Updated");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookSuccessfullyReturnsBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBook(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("Book Updated");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Book Updated")
        );
    }

    @Test
    public void testThatDeleteAuthorWhichDoesNotExistReturn204Status() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorOnExistReturn204Status() throws Exception {
        BookEntity testBookEntity = TestDataUtil.createTestBook(null);
        BookEntity savedBook = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/"+ savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
