package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUsersBooksCommand implements Command {
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<ActiveBook> activeBooks;
        Map<User,List<ActiveBook>> map = new HashMap<>();
        try {
            activeBooks = activeBookService.findAllUsersActiveBooks();
            for (ActiveBook activeBook : activeBooks){
                if(map.containsKey(activeBook.getUser())) {
                    map.get(activeBook.getUser()).add(activeBook);
                }else {
                    List<ActiveBook> tmp = new ArrayList<>();
                    tmp.add(activeBook);
                    map.put(activeBook.getUser(),tmp);
                }
            }
            req.setAttribute("users_books", map);
        } catch (ServiceException e) {
            //log
            System.out.println("Something went wronge");
            return new CommandResult("/",true);
        }
        return new CommandResult("/users_books.jsp");

    }
}
