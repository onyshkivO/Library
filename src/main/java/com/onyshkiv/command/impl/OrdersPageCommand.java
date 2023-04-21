package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersPageCommand implements Command {
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<ActiveBook> orders = activeBookService.findActiveBooksOrders();
            req.setAttribute("orders",orders);
            req.setAttribute("date",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            System.out.println("there");
        } catch (ServiceExcpetion e) {
            System.out.println("Something went wronge #OrdersPageCommand");
            System.out.println("there bad");
            return new CommandResult("/",true);
        }
        return new CommandResult("/users_orders.jsp");
    }
}
