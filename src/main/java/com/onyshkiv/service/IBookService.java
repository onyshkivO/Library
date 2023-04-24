package com.onyshkiv.service;

import com.onyshkiv.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Optional<Book> findBookById(Integer id) throws ServiceException;
    List<Book> findAllBooks() throws ServiceException;
    List<Book> findAllAvailableBooks() throws ServiceException;
    List<Book> findAllVailableBooksByName(String name) throws ServiceException;
    void createBook(Book book) throws ServiceException;
    void updateBook(Book book) throws ServiceException;
    void deleteBook(Book book) throws ServiceException;

    boolean isAvailableBook(Integer isbn) throws ServiceException;

}
