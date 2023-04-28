package com.onyshkiv.service;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByLogin(String login) throws ServiceException;
    public Optional<String> findUsePasswordByLogin(String login) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
    List<User> findLibrarians() throws ServiceException;
    List<User> findReaders() throws ServiceException;

    void createUser(User user) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    void deleteUser(User user) throws ServiceException;

    void changeUserPassword(String password,String login) throws ServiceException;

    public void changeUserLogin(String newLogin, String oldLogin) throws ServiceException;

}
