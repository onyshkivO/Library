package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class GetUserBookCommand implements Command {
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        List<ActiveBook> activeBooks;
        try {
            activeBooks = activeBookService.findBooksByUserLogin(user.getLogin());
            req.setAttribute("user_books", activeBooks);
        } catch (ServiceException e) {
            //log
            System.out.println("Something went wronge");
            return new CommandResult("/",true);
        }

        return new CommandResult("/user_books_info.jsp");
    }
}
