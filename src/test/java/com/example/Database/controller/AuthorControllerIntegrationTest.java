package com.example.Database.controller;

import com.example.Database.TestDataUtil;
import com.example.Database.domain.entities.AuthorEntity;
import com.example.Database.services.IAuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class AuthorControllerIntegrationTest {
    private MockMvc mockMvc;
    private IAuthorService authorService;
    private ObjectMapper objectMapper;
    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc,IAuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullySavedAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Iddrisu Sumaila")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("89")
        );
    }

    @Test
    public void testThatListAuthorSuccessfullyReturnsHttp20Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorSuccessfullyReturnsListAuthors() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.createAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Iddrisu Sumaila")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value("89")
        );
    }

    @Test
    public void testThatSingleAuthorSuccessfullyReturnsHttp20Ok() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.createAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/"+ testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatSingleAuthorSuccessfullyReturnsHttpNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatSingleAuthorSuccessfullyReturnAuthor() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        authorService.createAuthor(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/"+ testAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Iddrisu Sumaila")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("89")
        );
    }

    @Test
    public void testThatUpdateAuthorReturnsHttpNotFound() throws Exception {
        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntity);

        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+ savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntity);

        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(savedAuthor.getId());
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+ savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(savedAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())
        );
    }


    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200Ok() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntity);

        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setName("Iddrisu Updated");
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+ savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


    @Test
    public void testThatPartialUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntity);

        AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setName("Iddrisu Updated");
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+ savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Iddrisu Updated")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedAuthor.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorWhichDoesNotExistReturn204Status() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorOnExistReturn204Status() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
        AuthorEntity savedAuthor = authorService.createAuthor(testAuthorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/"+ savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
