package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.command.impl.admin.GetLibrariansCommand;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.PublicationService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetAddBookPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetLibrariansCommand.class);
    PublicationService publicationService = PublicationService.getInstance();
    AuthorService authorService = AuthorService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        transferDataBad(req);
        List<Publication> publications;
        List<Author> authors;
        try {
            publications = publicationService.findAllPublication();
            authors = authorService.findAllAuthors();
            req.setAttribute("publications", publications);
            req.setAttribute("authors", authors);
            req.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } catch (ServiceException e) {
            logger.error("Problem with authors/publication service occurred!(#GetAddBookPageCommand)", e);
            return new CommandResult("/", true);
        }
        logger.info("Admin successfully get add book page");
        return new CommandResult("/add_book.jsp");
    }

    private void transferDataBad(HttpServletRequest req) {
        HttpSession session = req.getSession();
        req.setAttribute("isbn", session.getAttribute("isbn"));
        session.removeAttribute("isbn");
        req.setAttribute("incorrect_isbn", session.getAttribute("incorrect_isbn"));
        session.removeAttribute("incorrect_isbn");
        req.setAttribute("name", session.getAttribute("name"));
        session.removeAttribute("name");
        req.setAttribute("publication_id", session.getAttribute("publication_id"));
        session.removeAttribute("publication_id");
        req.setAttribute("selected_authors", session.getAttribute("selected_authors"));
        session.removeAttribute("selected_authors");
        req.setAttribute("quantity", session.getAttribute("quantity"));
        session.removeAttribute("quantity");
        req.setAttribute("year_of_publication", session.getAttribute("year_of_publication"));
        session.removeAttribute("year_of_publication");
        req.setAttribute("details", session.getAttribute("details"));
        session.removeAttribute("details");
        req.setAttribute("date", session.getAttribute("date"));
        session.removeAttribute("date");
        req.setAttribute("already_exist_isbn", session.getAttribute("already_exist_isbn"));
        session.removeAttribute("already_exist_isbn");
    }
}
