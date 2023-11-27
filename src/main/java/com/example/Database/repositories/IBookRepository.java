package com.example.Database.repositories;

import com.example.Database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, String> {
//    Optional<Book> findByIsbn(String isbn);

}