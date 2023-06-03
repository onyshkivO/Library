package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.BookService;
import com.onyshkiv.service.impl.PublicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GetEditBookPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetEditBookPageCommand.class);
    BookService bookService = BookService.getInstance();
    PublicationService publicationService = PublicationService.getInstance();
    AuthorService authorService = AuthorService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String isbn = req.getParameter("isbn");
        List<Publication> publications;
        List<Author> authors;
        try {
            Optional<Book> optionalBook = bookService.findBookById(isbn);
            if (optionalBook.isEmpty()) {
                logger.info(String.format("There are not book with isbn %s", isbn));
                return new CommandResult("/", true);
            }
            Book book = optionalBook.get();

            publications = publicationService.findAllPublication();
            authors = authorService.findAllAuthors();
            req.setAttribute("book", book);
            req.setAttribute("publications", publications);
            req.setAttribute("authors", authors);
            req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } catch (ServiceException e) {
            logger.error("Problem with book, publication or author service occurred!", e);
            return new CommandResult("/", true);
        }
        logger.info("Admin successfully get edit book page");
        return new CommandResult("/edit_book.jsp");
    }
}
