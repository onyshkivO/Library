package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.onyshkiv.util.validation.Validation.validateLogin;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService = UserService.getInstance();
    private ActiveBookService activeBookService = ActiveBookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = "/login.jsp";
        boolean flag =false;

        String login = req.getParameter("login");
        req.setAttribute("login",login);

        if (!validateLogin(login)) {
            logger.info("invalid user login(#LoginCommand)");
            req.removeAttribute("login");
            req.setAttribute("incorrect_login", true);
            flag = true;
        }

        String password = req.getParameter("password");
        if (password.length() < 3) {
            logger.info("invalid user password(#LoginCommand)");
            req.setAttribute("incorrect_password", true);
            flag = true;
        }
        if (flag) {
            return new CommandResult(page);
        }

        try {
            Optional<String> optionalPassword = userService.findUsePasswordByLogin(login);
            Optional<User> optionalUser = userService.findUserByLogin(login);

            if (optionalPassword.isPresent()&&optionalUser.isPresent()) {
                User user = optionalUser.get();
                String passwordFromDB  = optionalPassword.get();

                if (PasswordHashGenerator.verify(password, passwordFromDB)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("user_role", user.getRole().getRoleId());
                    session.setAttribute("exist_user", true);
                    logger.info(String.format("user with login %s has been authorized",login));
                    page = "/user_profile.jsp";
                } else {
                    logger.info(String.format("password does not match for user with login %s",login));
                    req.setAttribute("password_does_not_match", true);
                    return new CommandResult(page);
                }
            }
            else {
                logger.info(String.format("Can not find user with login %s in database",login));
                req.setAttribute("incorrect_user", true);
                return new CommandResult(page);
            }

        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#LoginCommand)", e);
            return new CommandResult(page,true);//todo можливо тут краще перенаправляти на помилковсу сторінку
        }
        return new CommandResult(page, true);
    }
}
