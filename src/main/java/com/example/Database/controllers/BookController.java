package com.example.Database.controllers;

import com.example.Database.domain.dto.AuthorDto;
import com.example.Database.domain.dto.BookDto;
import com.example.Database.domain.entities.AuthorEntity;
import com.example.Database.domain.entities.BookEntity;
import com.example.Database.mappers.impl.BookMapperImpl;
import com.example.Database.services.IBookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookMapperImpl bookMapper;
    private final IBookService bookService;

    public BookController(BookMapperImpl bookMapper, IBookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExist = bookService.isExist(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);

        if (bookExist) {
            return  new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.OK);
        }else {
            return  new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
        }
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDto> PartialUpdate(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (!bookService.isExist(isbn)) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        return  new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity), HttpStatus.OK);
    }

//    @GetMapping("/books")
//    public List<BookDto> listBooks(){
//        List<BookEntity> books = bookService.findAll();
//        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());
//    }
@GetMapping("/books")
public Page<BookDto> listBooks(Pageable pageable){
    Page<BookEntity> books = bookService.findAll(pageable);
    return books.map(bookMapper::mapTo);

}

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> book = bookService.findBook(isbn);
        return book.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
