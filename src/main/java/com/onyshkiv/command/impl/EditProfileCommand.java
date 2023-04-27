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

public class EditProfileCommand implements Command {
    private final UserService userService= UserService.getInstance();
    private final ActiveBookService activeBookService = ActiveBookService.getInstance();
    private final Role ROLE= new Role(1);
    private final UserStatus STATUS= new UserStatus(1);
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page = "/edit_profile.jsp";

        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String phone = req.getParameter("phone");

        HttpSession session = req. getSession();
        User user  = (User)session.getAttribute("user");
        String oldLogin = user.getLogin();
        user = new User(oldLogin,email,"",ROLE,STATUS,firstName,lastName,phone);

        try{
            userService.updateUser(user);

            if (!oldLogin.equals(login)){
                if (userService.findUserByLogin(login).isEmpty()){
                    userService.changeUserLogin(login,oldLogin);
                    user.setLogin(login);
                }
                else{
                    req.setAttribute("bad_login",true);
                    session.setAttribute("user",user);
                    return new CommandResult("/edit_profile.jsp");
                }
            }
            session.setAttribute("user",user);
            page="/user_profile.jsp";
        } catch (ServiceException e) {
            e.printStackTrace();
            //log
            req.setAttribute("isWrong",true);
            return new CommandResult(page);
        }
        return new CommandResult(page,true);
    }
}
