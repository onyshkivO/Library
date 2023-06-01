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

public class RenamePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RenamePublicationCommand.class);
    PublicationService publicationService = PublicationService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("publication_id"));
        String name = req.getParameter("new_publication_name");
        Publication publication = new Publication(id,name);
        try{
            publicationService.updatePublication(publication);
        }catch (ServiceException e ){
            logger.error("Problem with publication service occurred!(#RenamePublicationCommand)", e);
            return new CommandResult("/controller?action=AuthAndPub",true);
        }
        logger.info(String.format("Admin successfully renamed publication with %d to %s", id, name));
        return new CommandResult("/controller?action=AuthAndPub",true);
    }
}
