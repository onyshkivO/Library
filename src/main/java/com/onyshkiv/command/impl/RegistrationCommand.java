package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RegistrationCommand implements Command {
    private final UserService userService= UserService.getInstance();
    private final Role ROLE= new Role(1);
    private final UserStatus STATUS= new UserStatus(1);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page="/registration.jsp";
        String login = req.getParameter("login");
        String email = req.getParameter("email").trim().isEmpty()?null:req.getParameter("email").trim();
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String phone = req.getParameter("phone");
        String pass = req.getParameter("password");
        System.out.println(login);
        User user = new User(login,email,pass,ROLE,STATUS,firstName,lastName,phone);
        try{
            userService.createUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            session.setAttribute("exist_user", true);
            page="/user_info.jsp";
        } catch (ServiceExcpetion e) {
            //log
            req.setAttribute("isWrong",true);
            return new CommandResult(page);
        }
        return new CommandResult(page,true); //todo good realization of redirect and more jsp pages
    }
}
