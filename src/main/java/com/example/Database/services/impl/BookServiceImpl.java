package com.example.Database.services.impl;

import com.example.Database.domain.entities.BookEntity;
import com.example.Database.repositories.IBookRepository;
import com.example.Database.services.IBookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements IBookService {
    private final IBookRepository bookRepository;

    public BookServiceImpl(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findBook(String isbn) {
        return bookRepository.findById(isbn);
    }
}
