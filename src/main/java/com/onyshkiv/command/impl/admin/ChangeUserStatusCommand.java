package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.entity.UserStatus;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ChangeUserStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeUserStatusCommand.class);
    UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isEmpty()) {
                logger.info(String.format("There are not user(reader) with login %s", login));
                return new CommandResult("/", true);
            }
            User user = optionalUser.get();
            if (user.getUserStatus().getUserStatusId() == 1) user.setUserStatus(new UserStatus(2));
            else user.setUserStatus(new UserStatus(1));
            userService.updateUser(user);
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!", e);
            return new CommandResult("/", true);
        }
        logger.info(String.format("User %s status was successfully changed", login));
        return new CommandResult("/controller?action=getReaders", true);
    }
}
