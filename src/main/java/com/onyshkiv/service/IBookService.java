package com.onyshkiv.service;

import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Optional<Book> findBookById(Integer id) throws ServiceExcpetion;
    List<Book> findAllBooks() throws ServiceExcpetion;
    void createBook(Book book) throws ServiceExcpetion;
    void updateBook(Book book) throws ServiceExcpetion;
    void deleteBook(Book book) throws ServiceExcpetion;

    boolean isAvailableBook(Integer isbn) throws ServiceExcpetion;

}
