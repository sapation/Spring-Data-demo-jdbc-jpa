package com.example.Database.services;

import com.example.Database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> getAllAuthor();

    Optional<AuthorEntity> findAuthor(Long id);

    boolean isExist(Long id);

    AuthorEntity save(AuthorEntity authorEntity);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void delete(Long id);
}
