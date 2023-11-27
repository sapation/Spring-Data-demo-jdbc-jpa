package com.example.Database.services;

import com.example.Database.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findBook(String isbn);
}
