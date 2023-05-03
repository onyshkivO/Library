package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.PublicationService;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetAddBookPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetLibrariansCommand.class);
    PublicationService publicationService = PublicationService.getInstance();
    AuthorService authorService = AuthorService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Publication> publications;
        List<Author> authors;
        try{
            publications = publicationService.findAllPublication();
            authors = authorService.findAllAuthors();
        }catch (ServiceException e){
            logger.error("Problem with authors/publication service occurred!(#GetAddBookPageCommand)", e);
            return new CommandResult("/", true); //todo another redirect
        }
        req.setAttribute("publications",publications);
        req.setAttribute("authors",authors);
        return new CommandResult("/add_book.jsp");
    }
}
