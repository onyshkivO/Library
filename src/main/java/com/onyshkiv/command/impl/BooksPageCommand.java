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
    private static Map<String, String> filterOptions = new HashMap<>();

    static {

        filterOptions.put("search_option", "");
    }


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Book> books;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String name = req.getParameter("name");
        String searchOption = req.getParameter("search_option");
        String sortOption = req.getParameter("sort_option");
        String sortOptionOrder = req.getParameter("sort_option_order");
        String spage = req.getParameter("page");
        Integer page = getPage(spage);

        Integer recordsPerPage = 3;
        Integer offset = (page - 1) * recordsPerPage;
        Integer noOfPages = 0;

        try {
            if (searchOption.equals("book_name")) {
                if (user != null && user.getRole().getRoleId() == 3) {
                    int noOfRecords = bookService.getNumberOfVailableBooksByName();
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllBooks(recordsPerPage, offset, sortOption, sortOptionOrder);
                } else {
                    int noOfRecords = bookService.getNumberOfVailableBooksByName();
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllVailableBooksByName(name, sortOption, sortOptionOrder);
                }
            } else {
                if (user != null && user.getRole().getRoleId() == 3) {
                    int noOfRecords = bookService.getNumberOfVailableBooksByAuthorName();
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllBooks(recordsPerPage, offset, sortOption, sortOptionOrder);
                } else {
                    int noOfRecords = bookService.getNumberOfVailableBooksByAuthorName();
                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                    books = bookService.findAllVailableBooksByAuthorName(name, sortOption, sortOptionOrder);
                }
            }
        } catch (ServiceException e) {
            logger.error("Problem with service occurred!", e);
            return new CommandResult("/controller?action=bookPage", true);
        }


//        if (name != null && !name.equals("") && searchOption.equals("book_name")) {
//            try {
//                if (user != null && user.getRole().getRoleId() == 3) {
//                    int noOfRecords = bookService.getNumberOfBooks();
//                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//                    books = bookService.findAllBooks(recordsPerPage, offset, sortOption, sortOptionOrder);
//                } else books = bookService.findAllVailableBooksByName(name, sortOption, sortOptionOrder);
//
//            } catch (ServiceException e) {
//                logger.error("Problem with service occurred!", e);
//                return new CommandResult("/controller?action=bookPage", true);
//            }
//        } else if (name != null && !name.equals("") && searchOption.equals("author_name")) {
//            try {
//                if (user != null && user.getRole().getRoleId() == 3) {
//
//                    int noOfRecords = bookService.getNumberOfBooks();
//                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//                    System.out.println(noOfRecords);
//                    System.out.println(noOfPages);
//                    books = bookService.findAllBooks(recordsPerPage, offset, sortOption, sortOptionOrder);
//                } else
//                    books = bookService.findAllVailableBooksByAuthorName(name, sortOption, sortOptionOrder);
//            } catch (ServiceException e) {
//                logger.error("Problem with service occurred!", e);
//                return new CommandResult("/controller?action=bookPage", true);
//            }
//        } else {
//
//            try {
//                if (user != null && user.getRole().getRoleId() == 3) {
//                    int noOfRecords = bookService.getNumberOfBooks();
//                    noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//                    if (sortOptionOrder != null && !sortOptionOrder.equals("") && sortOption != null && !sortOption.equals("")) {
//                        books = bookService.findAllBooks(recordsPerPage, offset, sortOption, sortOptionOrder);
//                    } else books = bookService.findAllBooks(recordsPerPage, offset, "b.name", "asc");
//                } else {
//                    if (sortOptionOrder != null && !sortOptionOrder.equals("") && sortOption != null && !sortOption.equals("")) {
//                        books = bookService.findAllAvailableBooks(sortOption, sortOptionOrder);
//                    } else books = bookService.findAllAvailableBooks("b.name", "asc");
//                }
//            } catch (ServiceException e) {
//                logger.error("Problem with service occurred!", e);
//                return new CommandResult("/controller?action=bookPage", true);
//            }
//        }


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

//        if (page < 1 || page > TOTAL_PAGES) {
//            logger.info("invalid page number format was received:" + pageNumberString);
//            page = 1;
//        }

        return page;
    }
}
