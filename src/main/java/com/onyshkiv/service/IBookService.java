package com.onyshkiv.service;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Optional<Book> findBookById(String id) throws ServiceException;
    List<Book> findAllVailableBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllVailableBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    List<Book> findAllBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException;
    Optional<Book> findEntityByIdAll(String id) throws ServiceException;



    void hideBook(String isbn) throws ServiceException;
    Integer findNumberOfAllVailableBooksByName(String name) throws ServiceException;
    Integer findNumberOfAllVailableBooksByAuthorName(String name) throws ServiceException;
    Integer findNumberOfAllBooksByName(String name) throws ServiceException;
    Integer findNumberOfAllBooksByAuthorName(String name) throws ServiceException;

    void createBook(Book book) throws ServiceException;
    void updateBook(Book book) throws ServiceException;
    void deleteBook(Book book) throws ServiceException;

    boolean isAvailableBook(String isbn) throws ServiceException;

}
