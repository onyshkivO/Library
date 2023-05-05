package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.PublicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenameAuthorCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RenameAuthorCommand.class);
    AuthorService authorService = AuthorService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("author_id"));
        String name = req.getParameter("new_author_name");
        Author author = new Author(id,name);
        try{
            authorService.updateAuthor(author);
        }catch (ServiceException e ){
            logger.error("Problem with author service occurred!(#RenameAuthorCommand)", e);
            //todo page for error
        }
        return new CommandResult("/controller?action=AuthAndPub",true);
    }
}
