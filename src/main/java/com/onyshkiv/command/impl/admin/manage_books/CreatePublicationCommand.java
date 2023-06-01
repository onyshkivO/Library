package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.PublicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreatePublicationCommand.class);
    PublicationService publicationService = PublicationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("publication_name");
        Publication publication = new Publication(name);
        try {
            publicationService.createPublication(publication);
        } catch (ServiceException e) {
            logger.error("Problem with publication service occurred!(#CreatePublicationCommand)", e);
            return new CommandResult("/controller?action=AuthAndPub", true);
        }
        logger.info(String.format("Admin successfully added new Publication %s", name));
        return new CommandResult("/controller?action=AuthAndPub", true);
    }
}
