package com.example.Database.services;

import com.example.Database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> getAllAuthor();

    Optional<AuthorEntity> findAuthor(Long id);
}