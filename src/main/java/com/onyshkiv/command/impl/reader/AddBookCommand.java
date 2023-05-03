package com.onyshkiv.command.impl.reader;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.command.impl.admin.GetReadersCommand;
import com.onyshkiv.entity.*;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class AddBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddBookCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    BookService bookService = BookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Integer isbn = Integer.valueOf(req.getParameter("isbn"));


        try {
            Book book = bookService.findBookById(isbn).get();

            if (activeBookService.findActiveBookByUserAndBook(user.getLogin(), isbn).isPresent()) {
                logger.info("user already has a book");
                System.out.println("user already has a book ");
                return new CommandResult("/controller?action=bookPage&already=true", true);
            }


            if (!bookService.isAvailableBook(book.getIsbn())) {
                logger.info("There are not available book(#AddBookCommand)");
                System.out.println("There are not available book(#AddBookCommand)");
                return new CommandResult("/controller?action=bookPage&notAvailable=true", true);
            }
            ActiveBook activeBook = new ActiveBook(book, user, new SubscriptionStatus(4), new Date(), new Date(), null);
            activeBookService.createActiveBook(activeBook);
        } catch (ServiceException e) {
            e.printStackTrace();
            //log
            System.out.println("something went wronge");
            return new CommandResult("/", true);

        }
//        req.setAttribute("active",true);
        return new CommandResult("/controller?action=bookPage&isbn=" + isbn, true);
    }
}
