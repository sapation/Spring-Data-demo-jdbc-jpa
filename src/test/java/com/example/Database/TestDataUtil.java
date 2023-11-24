package com.example.Database;

import com.example.Database.domain.Author;
import com.example.Database.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Iddrisu Sumaila")
                .age(89)
                .build();
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(2L)
                .name("Iddrisu Imoro")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(3L)
                .name("Iddrisu Umar")
                .age(21)
                .build();
    }
    public static Book createTestBook() {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("218-1-2345-6789-1")
                .title("The programming in java")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("458-1-2345-6789-2")
                .title("The shadow of programming")
                .authorId(1L)
                .build();
    }
}
