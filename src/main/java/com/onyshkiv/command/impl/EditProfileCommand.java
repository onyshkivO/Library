package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.onyshkiv.util.validation.Validation.*;

public class EditProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);
    private final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        String login = req.getParameter("login");
        req.setAttribute("login", login);
        if (!validateLogin(login)) {
            req.removeAttribute("login");
            req.setAttribute("incorrect_login", true);
            flag = true;
        }

        String email = req.getParameter("email");
        req.setAttribute("email", email);
        if (!validateEmail(email)) {
            req.removeAttribute("email");
            req.setAttribute("incorrect_Email", true);
            flag = true;
        }

        String firstName = req.getParameter("first_name");
        req.setAttribute("first_name", firstName);
        if (!validateName(firstName)) {
            req.removeAttribute("first_name");
            req.setAttribute("incorrect_firstName", true);
            flag = true;
        }

        String lastName = req.getParameter("last_name");
        req.setAttribute("last_name", lastName);
        if (!validateName(lastName)) {
            req.removeAttribute("last_name");
            req.setAttribute("incorrect_lastName", true);
            flag = true;
        }
        String phone = req.getParameter("phone");
        req.setAttribute("phone", phone);
        if (!validatePhone(phone)) {
            req.removeAttribute("phone");
            req.setAttribute("incorrect_phone", true);
            flag = true;
        }

        if (flag) {
            logger.info("Invalid data to edit(#EditProfileCommand)");
            return new CommandResult("/edit_profile.jsp");
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String oldLogin = user.getLogin();
        user = new User(oldLogin, email, "", user.getRole(), user.getUserStatus(), firstName, lastName, phone);

        try {
            userService.updateUser(user);

            if (!oldLogin.equals(login)) {
                if (userService.findUserByLogin(login).isEmpty()) {
                    userService.changeUserLogin(login, oldLogin);
                    user.setLogin(login);
                } else {
                    logger.info("Login already exist(#EditProfileCommand)");
                    req.setAttribute("already_exist_login", true);
                    session.setAttribute("user", user);
                    return new CommandResult("/edit_profile.jsp");
                }
            }
            session.setAttribute("user", user);
            logger.info("Successfully user data update(#EditProfileCommand)");
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("Problem with user service occurred!(#EditProfileCommand)", e);
            return new CommandResult("/edit_profile.jsp");
        }
        return new CommandResult("/user_profile.jsp", true);
    }
}
