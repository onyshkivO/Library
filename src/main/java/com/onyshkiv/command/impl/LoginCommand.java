package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.onyshkiv.util.validation.Validation.validateLogin;
import static com.onyshkiv.util.validation.Validation.validatePassword;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;

        String login = req.getParameter("login");
        req.setAttribute("login", login);

        if (!validateLogin(login)) {
            logger.info("invalid user login(#LoginCommand)");
            req.removeAttribute("login");
            req.setAttribute("incorrect_login", true);
            flag = true;
        }

        String pass = req.getParameter("password");
        req.setAttribute("password", pass);
        if (!validatePassword(pass)) {
            req.removeAttribute("password");
            req.setAttribute("incorrect_password", true);
            flag = true;
        }


        if (flag) {
            logger.info("Invalid data to login(#LoginCommand)");
            return new CommandResult("/login.jsp");
        }

        try {
            Optional<String> optionalPassword = userService.findUsePasswordByLogin(login);
            Optional<User> optionalUser = userService.findUserByLogin(login);

            if (optionalPassword.isPresent() && optionalUser.isPresent()) {
                User user = optionalUser.get();
                String passwordFromDB = optionalPassword.get();

                if (PasswordHashGenerator.verify(pass, passwordFromDB)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("user_role", user.getRole().getRoleId());
                    session.setAttribute("exist_user", true);
                    logger.info(String.format("user with login %s has been authorized", login));
                } else {
                    logger.info(String.format("password does not match for user with login %s(#LoginCommand)", login));
                    req.setAttribute("password_does_not_match", true);
                    return new CommandResult("/login.jsp");
                }
            } else {
                logger.info(String.format("Can not find user with login %s in database(#LoginCommand)", login));
                req.setAttribute("incorrect_user", true);
                return new CommandResult("/login.jsp");
            }

        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#LoginCommand)", e);
            return new CommandResult("/login.jsp", true);
        }
        return new CommandResult("/user_profile.jsp", true);
    }
}
