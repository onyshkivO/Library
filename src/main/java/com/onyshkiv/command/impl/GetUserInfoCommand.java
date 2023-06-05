package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class GetUserInfoCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUserInfoCommand.class);
    UserService userService = UserService.getInstance();
    ActiveBookService activeBookService = ActiveBookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        List<ActiveBook> activeBooks;
        User user = null;
        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isEmpty()) {
                return new CommandResult("/", true);
            }
            user = optionalUser.get();
            activeBooks = activeBookService.findBooksByUserLogin(user.getLogin());
            req.setAttribute("user_books", activeBooks);
            req.setAttribute("user", user);
            logger.info(String.format("search for %s info(#GetUserInfoCommand)", login));
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#GetUserInfoCommand)", e);
            return new CommandResult("/", true);
        }
        return new CommandResult("/user_info.jsp");
    }

}
