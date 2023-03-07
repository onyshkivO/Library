package com.onyshkiv.service;

import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByLogin(String login) throws ServiceExcpetion;
    List<User> findAllUsers() throws ServiceExcpetion;
    void createUser(User user) throws ServiceExcpetion;
    void updateUser(User user) throws ServiceExcpetion;
    void deleteUser(User user) throws ServiceExcpetion;
    void changeUserPassword(User user)throws ServiceExcpetion;

}
