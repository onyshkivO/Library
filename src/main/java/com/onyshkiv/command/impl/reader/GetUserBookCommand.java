package com.onyshkiv.command.impl.reader;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetUserBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetUserBookCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        List<ActiveBook> activeBooks;
        try {
            activeBooks = activeBookService.findBooksByUserLogin(user.getLogin());
            req.setAttribute("user_books", activeBooks);
            logger.info(String.format("User %s successfully get his page(#GetUserBookCommand)", user.getLogin()));
        } catch (ServiceException e) {
            logger.error("Problem with activeBook service occurred!(#GetUserBookCommand)", e);
            return new CommandResult("/", true);
        }

        return new CommandResult("/user_books_info.jsp");
    }
}
