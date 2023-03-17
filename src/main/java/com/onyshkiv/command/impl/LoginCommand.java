package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    private UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = "/login.jsp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (PasswordHashGenerator.verify(password, user.getPassword())) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("exist_user", true);
                    page = "/user_info.jsp";
                } else {
                    //log
                    req.setAttribute("isWrong", true);
                    return new CommandResult(page);
                }
            }
            else {
                req.setAttribute("isWrong", true);
                return new CommandResult(page);
            }

        } catch (ServiceExcpetion e) {
            //log
            req.setAttribute("isWrong", true);
            e.printStackTrace();
            return new CommandResult(page);
        }
        return new CommandResult(page, true);
    }
}
