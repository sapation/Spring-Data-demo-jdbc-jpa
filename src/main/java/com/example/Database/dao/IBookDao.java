package com.example.Database.dao;

import com.example.Database.dao.impl.BookDaoImpl;
import com.example.Database.domain.Book;

import java.util.List;
import java.util.Optional;

public interface IBookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> find();

    void update(String isbn, Book book);

    void delete(String isbn);
}
