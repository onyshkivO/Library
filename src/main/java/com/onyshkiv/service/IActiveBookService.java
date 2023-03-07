package com.onyshkiv.service;

import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.User;

import java.util.List;
import java.util.Optional;

public interface IActiveBookService {
    Optional<ActiveBook> findActiveBookById(Integer id) throws ServiceExcpetion;
    List<ActiveBook> findAllActiveBooks() throws ServiceExcpetion;
    void createActiveBook(ActiveBook activeBook) throws ServiceExcpetion;
    void updateActiveBook(ActiveBook activeBook) throws ServiceExcpetion;
    void deleteActiveBook(ActiveBook activeBook) throws ServiceExcpetion;
}
