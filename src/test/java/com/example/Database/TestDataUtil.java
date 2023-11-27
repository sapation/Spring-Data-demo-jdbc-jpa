package com.example.Database;

import com.example.Database.domain.dto.AuthorDto;
import com.example.Database.domain.dto.BookDto;
import com.example.Database.domain.entities.AuthorEntity;
import com.example.Database.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Iddrisu Sumaila")
                .age(89)
                .build();
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Iddrisu Imoro")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Iddrisu Umar")
                .age(21)
                .build();
    }
    public static BookEntity createTestBook(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("218-1-2345-6789-1")
                .title("The programming in java")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("458-1-2345-6789-2")
                .title("The shadow of programming")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createTestBookDto(AuthorDto author){
        return BookDto.builder()
                .isbn("458-1-2345-6789-2")
                .title("The shadow of programming")
                .authorEntity(author)
                .build();
    }
}
