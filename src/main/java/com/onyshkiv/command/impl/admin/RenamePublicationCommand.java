package com.onyshkiv.command.impl.admin;

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

public class RenamePublicationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RenamePublicationCommand.class);
    PublicationService publicationService = PublicationService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.valueOf(req.getParameter("publication_id"));
        String name = req.getParameter("new_publication_name");
        Publication publication = new Publication(id,name);
        try{
            publicationService.updatePublication(publication);
        }catch (ServiceException e ){
            logger.error("Problem with publication service occurred!(#RenamePublicationCommand)", e);
            //todo page for error
        }
        return new CommandResult("/controller?action=AuthAndPub",true);
    }
}
