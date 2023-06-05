package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.onyshkiv.util.validation.Validation.*;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final UserService userService = UserService.getInstance();
    private final UserStatus STATUS = new UserStatus(1);

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

        String pass = req.getParameter("password");

        req.setAttribute("password", pass);
        if (!validatePassword(pass)) {
            req.removeAttribute("password");
            req.setAttribute("incorrect_password", true);
            flag = true;
        }


        Role role = new Role(req.getParameter("role"));

        if (flag) {
            logger.info("Invalid data to register(#RegistrationCommand)");
            return role.getRoleId() == 1 ? new CommandResult("/registration.jsp") : new CommandResult("/register_librarian.jsp");
        }
        if (role.getRoleId() == 2 && (((User) req.getSession().getAttribute("user")) == null || ((User) req.getSession().getAttribute("user")).getRole().getRoleId() != 3))
            return new CommandResult("/", true);
        User user = new User(login, email, pass, role, STATUS, firstName, lastName, phone);
        try {
            if(userService.findUserByLogin(login).isPresent()){
                logger.info("Login already exist(#RegistrationCommand)");
                req.setAttribute("already_exist_login", true);
                return role.getRoleId() == 1 ? new CommandResult("/registration.jsp") : new CommandResult("/register_librarian.jsp");
            }

            userService.createUser(user);
            if (role.getRoleId() == 1) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("user_role", user.getRole().getRoleId());
                session.setAttribute("exist_user", true);
            }
            logger.info("User successfully registered(#RegistrationCommand)");
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#RegistrationCommand)", e);
            return role.getRoleId() == 1 ? new CommandResult("/registration.jsp") : new CommandResult("/register_librarian.jsp");
        }
        return role.getRoleId() == 1 ? new CommandResult("/user_profile.jsp", true) : new CommandResult("/controller?action=getLibrarians", true);
    }
}
