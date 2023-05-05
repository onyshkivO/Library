package com.onyshkiv.command.impl.admin;

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

import java.util.List;

public class GetEditBookPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetEditBookPageCommand.class);
    BookService bookService = BookService.getInstance();
    PublicationService publicationService = PublicationService.getInstance();
    AuthorService authorService = AuthorService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer isbn = Integer.valueOf(req.getParameter("isbn"));
        List<Publication> publications;
        List<Author> authors;
        try {
            Book book = bookService.findBookById(isbn).get();//todo something with .get everywhere
            publications = publicationService.findAllPublication();
            authors = authorService.findAllAuthors();
            req.setAttribute("book",book);
            req.setAttribute("publications",publications);
            req.setAttribute("authors",authors);

        }catch (ServiceException e){
            logger.error("Problem with book publication service occurred!(#GetEditBookPageCommand)", e);
            return new CommandResult("/", true); //todo another redirect maybe page for 404 or 505 error
        }
        return new CommandResult("/edit_book.jsp");
    }
}
