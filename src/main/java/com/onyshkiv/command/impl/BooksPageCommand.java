package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BooksPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BooksPageCommand.class);
    BookService bookService  = BookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Book> books;
        String name = req.getParameter("name");
        String searchOption = req.getParameter("search_option");


        if (name != null && !name.equals("")&&searchOption.equals("book_name")) {
            try {
                books = bookService.findAllVailableBooksByName(name);
            } catch ( ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage",true); //todo another redirect
            }
        }else if (name != null && !name.equals("")&&searchOption.equals("author_name")){
            try {
                books = bookService.findAllVailableBooksByAuthorName(name);
            } catch ( ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage",true); //todo another redirect
            }
        }
        else {
            try {
                books = bookService.findAllBooks();
            } catch (ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage",true); //todo another redirect
            }
        }

        req.setAttribute("books",books);
        req.setAttribute("name",name);
        return new CommandResult("/books.jsp");
    }
}
