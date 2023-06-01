package com.onyshkiv.service;

import com.onyshkiv.entity.ActiveBook;


import java.util.List;
import java.util.Optional;
import java.util.Date;

public interface IActiveBookService {
    Optional<ActiveBook> findActiveBookById(Integer id) throws ServiceException;

    List<ActiveBook> findAllActiveBooks() throws ServiceException;


    List<ActiveBook> findActiveBooksOrders()throws ServiceException;

    List<ActiveBook> findBooksByUserLogin(String login) throws ServiceException;
    List<ActiveBook> findAllUsersActiveBooks() throws ServiceException;
    Optional<ActiveBook> findActiveBookByUserAndBook(String login,String isbn) throws ServiceException;

    void createActiveBook(ActiveBook activeBook) throws ServiceException;

    void updateActiveBook(ActiveBook activeBook) throws ServiceException;
    void updateActiveBookForGive(Integer id, Date endDate, Double fine) throws ServiceException;
     void updateActiveBookForGiveBack(Integer id) throws ServiceException;

    void deleteActiveBook(ActiveBook activeBook) throws ServiceException;
}
