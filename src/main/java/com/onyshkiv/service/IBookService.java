package com.onyshkiv.service;

import com.onyshkiv.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Optional<Book> findBookById(Integer id) throws ServiceException;
//    List<Book> findAllBooks() throws ServiceException;
//     List<Book> findAllBooks(Integer booksPerPage,Integer offset, String sortOption, String orderOption) throws ServiceException;
//    List<Book> findAllBooks(String sortOption, String orderOption) throws ServiceException;
//    List<Book> findAllAvailableBooks(String sortOption, String orderOption) throws ServiceException;
//    Integer getNumberOfAvailableBooks()throws ServiceException;
    List<Book> findAllVailableBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllVailableBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;




    Integer findNumberOfAllVailableBooksByName(String name) throws ServiceException;
    Integer findNumberOfAllVailableBooksByAuthorName(String name) throws ServiceException;
    Integer findNumberOfAllBooksByName(String name) throws ServiceException;
    Integer findNumberOfAllBooksByAuthorName(String name) throws ServiceException;

    void createBook(Book book) throws ServiceException;
    void updateBook(Book book) throws ServiceException;
    void deleteBook(Book book) throws ServiceException;

    boolean isAvailableBook(Integer isbn) throws ServiceException;

}
