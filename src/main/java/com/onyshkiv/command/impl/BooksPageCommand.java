package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class BooksPageCommand implements Command {
    BookService bookService  = BookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        try {

            List<Book> books = bookService.findAllAvailableBooks();
            req.setAttribute("books",books);
        } catch (ServiceExcpetion e) {
            System.out.println("Something went wronge #BooksPageCommand");
            return new CommandResult("/",true);
        }
        return new CommandResult("/books.jsp");
    }
}
