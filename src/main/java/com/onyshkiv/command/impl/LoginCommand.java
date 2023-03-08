package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Role;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private UserService userService = UserService.getInstance();
    private Role role = new Role(1);
    private UserStatus userStatus = new UserStatus(1);
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String page;
        String login = req.getParameter("login");
        if(login==null ||login.isEmpty()){// todo validation
            //log
            return new CommandResult("/error.jsp",true);
        }
        String password = req.getParameter("password");
        if(login==null ||login.isEmpty()){// todo validation
            //log
            return new CommandResult("/error.jsp",true);
        }
        password= PasswordHashGenerator.hash(password);

        String email = req.getParameter("email");
        if(email==null ||email.isEmpty()){// todo validation
            //log
            return new CommandResult("/error.jsp",true);
        }
        String firstName = req.getParameter("firstName");
        if(firstName==null ||firstName.isEmpty()){// todo validation
            //log
            return new CommandResult("/error.jsp",true);
        }

        String lastName = req.getParameter("lastName");
        if(lastName==null ||lastName.isEmpty()){// todo validation
            //log
            return new CommandResult("/error.jsp",true);
        }
        String phone =req.getParameter("phone");

        try{
            User user = new User(login,email,password,role,userStatus,firstName,lastName,phone);
            userService.createUser(user);
            HttpSession session = req.getSession();
            session.setAttribute("user",user);
            page = "/good.jsp";
        }catch (ServiceExcpetion e){
            //log
            return new CommandResult("/error.jsp");
        }
        return new CommandResult(page,true);



    }
}
