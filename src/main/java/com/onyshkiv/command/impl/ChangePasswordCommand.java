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

public class ChangePasswordCommand implements Command {
    UserService userService = UserService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String oldPassword = req.getParameter("password_old");
        String newPassword = req.getParameter("password_new");


        try{
            String passwordFromDB = userService.findUsePasswordByLogin(user.getLogin()).get();
            if (PasswordHashGenerator.verify(oldPassword,passwordFromDB)){
                userService.changeUserPassword(PasswordHashGenerator.hash(newPassword), user.getLogin());
            }
            else{
                req.setAttribute("bad_old_password",true);
                return new CommandResult("/change_password.jsp");
            }
        } catch (ServiceExcpetion e) {
            e.printStackTrace();
            //log
            req.setAttribute("isWrong",true);
            return new CommandResult("/change_password.jsp");
        }
        return new CommandResult("/edit_profile.jsp",true);
    }
}
