package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    UserService userService = UserService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");

        try{
            User userToDeleting = userService.findUserByLogin(login).get();
            userService.deleteUser(userToDeleting);
            logger.info(String.format("Librarian %s has been deleted.",login));
        }catch (ServiceException e){
            logger.error("Problem with user service occurred!(#DeleteUserCommand->findUserByLogin)", e);
            return new CommandResult("/controller?action=getLibrarians", true); //todo another redirect
        }
        //todo maybe some info, that user has been deleted (like in books.jsp when book added)

        return new CommandResult("/controller?action=getLibrarians",true);
    }
}
