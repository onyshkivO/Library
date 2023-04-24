package com.onyshkiv.service;

import com.onyshkiv.entity.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    List<Author> findAllAuthors() throws ServiceException;
    Optional<Author> findAuthorById(Integer id) throws ServiceException;
    void createAuthor(Author author) throws ServiceException;
    void updateAuthor(Author author) throws ServiceException;
    void deleteAuthor(Author author) throws ServiceException;




}
