package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.AuthorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateAuthorCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateAuthorCommand.class);
    AuthorService authorService = AuthorService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("author_name");
        Author author = new Author(name);
        try {
            authorService.createAuthor(author);
        } catch (ServiceException e) {
            logger.error("Problem with author service occurred!(#CreateAuthorCommand)", e);
            return new CommandResult("/controller?action=AuthAndPub", true);
        }
        logger.info(String.format("Admin successfully added new Author %s", name));
        return new CommandResult("/controller?action=AuthAndPub", true);
    }
}
