package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BooksPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BooksPageCommand.class);
    private BookService bookService = BookService.getInstance();
    private static Map<String, String> sortOptions = new HashMap<>();

    static {
        sortOptions.put("date_of_publication", "date_of_publication");
        sortOptions.put("b.name", "b.name");
        sortOptions.put("authors", "authors");
        sortOptions.put("p.name", "p.name");
    }


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Book> books;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String name = req.getParameter("name");

        String searchOption = req.getParameter("search_option");
        searchOption = (searchOption!=null&&searchOption.equals("author_name")) ?"author_name":"book_name";

        String sortOption = req.getParameter("sort_option");
        sortOption = sortOptions.getOrDefault(sortOption,"b.name");

        String sortOptionOrder = req.getParameter("sort_option_order");
        sortOptionOrder =(sortOptionOrder!=null&&sortOptionOrder.equals("desc"))?"desc":"asc";
        String spage = req.getParameter("page");
        Integer page = getPage(spage);

        Integer recordsPerPage = 3;
        Integer offset = (page - 1) * recordsPerPage;
        Integer noOfPages;

        try {
            if (searchOption.equals("book_name")) {
                if (user != null && user.getRole().getRoleId() == 3) {
                    int noOfRecords = bookService.findNumberOfAllBooksByName(name);
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllBooksByName(name, sortOption, sortOptionOrder, recordsPerPage, offset);
                } else {
                    int noOfRecords = bookService.findNumberOfAllVailableBooksByName(name);
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllVailableBooksByName(name, sortOption, sortOptionOrder, recordsPerPage, offset);
                }
            } else {
                if (user != null && user.getRole().getRoleId() == 3) {
                    int noOfRecords = bookService.findNumberOfAllBooksByAuthorName(name);
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllBooksByAuthorName(name, sortOption, sortOptionOrder, recordsPerPage, offset);
                } else {
                    int noOfRecords = bookService.findNumberOfAllVailableBooksByAuthorName(name);
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllVailableBooksByAuthorName(name, sortOption, sortOptionOrder, recordsPerPage, offset);
                }
            }
        } catch (ServiceException e) {
            logger.error("Problem with service occurred!", e);
            return new CommandResult("/controller?action=bookPage", true);
        }

        req.setAttribute("books", books);
        req.setAttribute("search_option", searchOption);
        req.setAttribute("sort_option", sortOption);
        req.setAttribute("sort_option_order", sortOptionOrder);
        req.setAttribute("name", name);
        req.setAttribute("page", page);
        req.setAttribute("noOfPages", noOfPages);


        return new CommandResult("/books.jsp");
    }

    private Integer getPage(String pageNumberString) {

        pageNumberString = pageNumberString == null ? "1" : pageNumberString;
        Integer page = 1;
        try {
            page = Integer.parseInt(pageNumberString);
        } catch (NumberFormatException e) {
            logger.info("invalid page number format was received:" + pageNumberString);
        }


        return page;
    }
}
