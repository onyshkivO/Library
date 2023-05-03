package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.SubscriptionStatus;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GiveBookBackCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GiveBookBackCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {//todo як правильно, робити метод в дао, який буде міняти щось одне(в даному випадку статус) по id чи по id діставати дані з бази і потім в об'єкті міняти щосб і тоді update метод дао
        Integer activeBookId= Integer.valueOf(req.getParameter("active_book_id"));
        try {
            ActiveBook activeBook = activeBookService.findActiveBookById(activeBookId).get();
            activeBook.setSubscriptionStatus(new SubscriptionStatus(2));
            activeBookService.updateActiveBook(activeBook);
        }catch (ServiceException e){
            logger.error("Problem with active book service occurred!(#GiveBookBackCommand", e);
            return new CommandResult("/", true); //todo another redirect
        }
        return new CommandResult("/controller?action=getUsersBook",true);
    }
}