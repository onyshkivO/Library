package com.onyshkiv.service;

import com.onyshkiv.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findUserByLogin(String login) throws ServiceExcpetion;
    public Optional<String> findUsePasswordByLogin(String login) throws ServiceExcpetion;
    List<User> findAllUsers() throws ServiceExcpetion;

    void createUser(User user) throws ServiceExcpetion;

    void updateUser(User user) throws ServiceExcpetion;

    void deleteUser(User user) throws ServiceExcpetion;

    void changeUserPassword(String password,String login) throws ServiceExcpetion;

    public void changeUserLogin(String newLogin, String oldLogin) throws ServiceExcpetion;

}
