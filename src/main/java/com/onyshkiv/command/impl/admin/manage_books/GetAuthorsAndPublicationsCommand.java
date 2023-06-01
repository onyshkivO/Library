package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.PublicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GetAuthorsAndPublicationsCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetAuthorsAndPublicationsCommand.class);
    AuthorService authorService = AuthorService.getInstance();
    PublicationService publicationService = PublicationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Author> authors;
        List<Publication> publications;
        try {
            authors = authorService.findAllAuthors();
            publications = publicationService.findAllPublication();
            req.setAttribute("authors", authors);
            req.setAttribute("publications", publications);
        } catch (ServiceException e) {
            logger.error("Problem with author publication service occurred!(#GetAuthorsAndPublicationsCommand)", e);
            return new CommandResult("/", true);
        }
        logger.info("Admin successfully get authors and publications page");
        return new CommandResult("/authors_publications.jsp");
    }
}
