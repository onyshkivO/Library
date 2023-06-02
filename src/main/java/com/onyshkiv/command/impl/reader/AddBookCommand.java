package com.onyshkiv.command.impl.reader;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.*;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;
import java.util.Optional;

public class AddBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddBookCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    BookService bookService = BookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String isbn = req.getParameter("isbn");

        try {
            Optional<Book> bookOptional = bookService.findBookById(isbn);
            if (bookOptional.isEmpty()) {
                logger.info("There are not available book(Optional = null)(#AddBookCommand)");
                resp.addCookie(new Cookie("notAvailable", "true"));
                return new CommandResult("/controller?action=bookPage&page=1", true);
            }
            Book book = bookOptional.get();
            if (activeBookService.findActiveBookByUserAndBook(user.getLogin(), isbn).isPresent()) {
                logger.info("user already has a book");
                System.out.println("user already has a book ");
                resp.addCookie(new Cookie("already", "true"));
                return new CommandResult("/controller?action=bookPage&page=1", true);
            }
            if (!bookService.isAvailableBook(book.getIsbn())) {
                logger.info("There are not available book(#AddBookCommand)");
                System.out.println("There are not available book(#AddBookCommand)");
                resp.addCookie(new Cookie("notAvailable", "true"));
                return new CommandResult("/controller?action=bookPage&page=1", true);
            }
            ActiveBook activeBook = new ActiveBook(book, user, new SubscriptionStatus(4), new Date(), new Date(), null);
            activeBookService.createActiveBook(activeBook);
        } catch (ServiceException e) {
            logger.error("Problem with active book or book service occurred!", e);
            return new CommandResult("/controller?action=bookPage&page=1", true);

        }
        logger.info(String.format("User %s successfully ordered book with isbn %s", user.getLogin(), isbn));
        resp.addCookie(new Cookie("success", "true"));
        return new CommandResult("/controller?action=bookPage&page=1", true);
    }
}
