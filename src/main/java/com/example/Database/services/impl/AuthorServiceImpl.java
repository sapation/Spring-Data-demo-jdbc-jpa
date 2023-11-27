package com.example.Database.services.impl;

import com.example.Database.domain.entities.AuthorEntity;
import com.example.Database.repositories.IAuthorRepository;
import com.example.Database.services.IAuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements IAuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorServiceImpl(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> getAllAuthor() {
        return StreamSupport.stream(
                authorRepository.findAll().spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findAuthor(Long id) {
        return authorRepository.findById(id);
    }
}
