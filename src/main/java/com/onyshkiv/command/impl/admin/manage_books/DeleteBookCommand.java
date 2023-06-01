package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteBookCommand.class);
    BookService bookService = BookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String isbn = req.getParameter("isbn");
        try {
            bookService.hideBook(isbn);
            logger.info(String.format("Book with isbn %s has been hidden.", isbn));
        } catch (ServiceException e) {
            logger.error("Problem with book service occurred!", e);
            return new CommandResult("/controller?action=bookPage&page=1", true);
        }
        logger.info(String.format("Book with isbn %s was successfully hidden", isbn));
        return new CommandResult("/controller?action=bookPage&page=1", true);
    }
}
