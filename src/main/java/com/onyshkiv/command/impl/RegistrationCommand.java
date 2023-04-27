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

import static com.onyshkiv.util.validation.Validation.*;

public class RegistrationCommand implements Command {
    private final UserService userService = UserService.getInstance();
    private final Role READER_ROLE = new Role(1);
    private final Role LIABRARIAN_ROLE = new Role(2);
    private final UserStatus STATUS = new UserStatus(1);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = "/registration.jsp";

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
            phone = null;
        }

        String pass = req.getParameter("password");
        req.setAttribute("password", pass);
        if (pass.length() < 3) {
            req.removeAttribute("password");
            req.setAttribute("incorrect_password", true);
            flag = true;
        }


        if (flag)
            return new CommandResult(page);

        Role role = new Role(req.getParameter("role"));
        User user = new User(login, email, pass, role, STATUS, firstName, lastName, phone);
        try {
            userService.createUser(user);
            if(role.getRoleId()==1) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("user_role", user.getRole().getRoleId());
                session.setAttribute("exist_user", true);
            }
        } catch (ServiceException e) {
            //log
            req.setAttribute("already_exist_login", true);
            return role.getRoleId()==1? new CommandResult("/registration.jsp"): new CommandResult("/register_librarian.jsp");
        }
        return role.getRoleId()==1? new CommandResult("/user_profile.jsp",true): new CommandResult("/controller?action=getLibrarians",true);
    }
}
