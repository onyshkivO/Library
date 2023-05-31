package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.command.impl.BooksPageCommand;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(OrdersPageCommand.class);
    private ActiveBookService activeBookService = ActiveBookService.getInstance();


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<ActiveBook> orders = activeBookService.findActiveBooksOrders();
            req.setAttribute("orders", orders);
            req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            logger.info(String.format("Librarian successfully get orders page, that contains %d orders ", orders.size()));
        } catch (ServiceException e) {
            logger.error("Problem with active Book service occurred!", e);
            return new CommandResult("/", true);
        }
        return new CommandResult("/users_orders.jsp");
    }
}
