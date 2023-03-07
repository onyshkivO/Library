package com.onyshkiv.service;

import com.onyshkiv.entity.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    List<Author> findAllAuthors() throws ServiceExcpetion;
    Optional<Author> findAuthorById(Integer id) throws ServiceExcpetion;
    void createAuthor(Author author) throws ServiceExcpetion;
    void updateAuthor(Author author) throws ServiceExcpetion;
    void deleteAuthor(Author author) throws ServiceExcpetion;




}
