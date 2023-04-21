package com.onyshkiv.service;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.User;


import java.util.List;
import java.util.Optional;
import java.util.Date;

public interface IActiveBookService {
    Optional<ActiveBook> findActiveBookById(Integer id) throws ServiceExcpetion;

    List<ActiveBook> findAllActiveBooks() throws ServiceExcpetion;

    List<ActiveBook> findActiveBooksOrders()throws ServiceExcpetion;

    List<ActiveBook> findBooksByUserLogin(String login) throws ServiceExcpetion;
    List<ActiveBook> findAllUsersActiveBooks() throws ServiceExcpetion;
    Optional<ActiveBook> findActiveBookByUserAndBook(String login,Integer isbn) throws ServiceExcpetion ;

    void createActiveBook(ActiveBook activeBook) throws ServiceExcpetion;

    void updateActiveBook(ActiveBook activeBook) throws ServiceExcpetion;
    void updateActiveBookForGive(Integer id, Date endDate, Double fine) throws ServiceExcpetion;

    void deleteActiveBook(ActiveBook activeBook) throws ServiceExcpetion;
}
