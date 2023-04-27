package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUserInfoCommand.class);
    UserService userService = UserService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        try{
            User user = userService.findUserByLogin(login).get();
            req.setAttribute("user",user);
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#GetUserInfoCommand->findUserByLogin)", e);
            return new CommandResult("/", true); //todo another redirect
        }
        return new CommandResult("/user_info.jsp");
    }
}
