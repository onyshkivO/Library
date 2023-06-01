package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);
    UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");

        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isEmpty()) {
                logger.info(String.format("There are not user(librarian) with login %s", login));
                return new CommandResult("/controller?action=getLibrarians", true);
            }
            User userToDeleting = optionalUser.get();
            userService.deleteUser(userToDeleting);

        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!", e);
            return new CommandResult("/controller?action=getLibrarians", true);
        }
        logger.info(String.format("Librarian %s has been deleted.", login));
        return new CommandResult("/controller?action=getLibrarians", true);
    }
}
