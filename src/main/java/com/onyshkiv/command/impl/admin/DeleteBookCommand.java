package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.command.impl.librarian.DeleteUserCommand;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.BookService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteBookCommand.class);
    BookService bookService = BookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer isbn = Integer.valueOf(req.getParameter("isbn"));
        try {
            bookService.hideBook(isbn);
            logger.info(String.format("Book with isbn %d has been hiden.", isbn));
        } catch (ServiceException e) {
            logger.error("Problem with book service occurred!(#DeleteBookCommand)", e);
            return new CommandResult("/controller?action=bookPage&page=1", true);
        }
        return new CommandResult("/controller?action=bookPage&page=1", true);
    }
}
