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

import java.util.List;

public class GetLibrariansCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetLibrariansCommand.class);
    UserService userService = UserService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> librarians;
        try{
            librarians = userService.findLibrarians();
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(findLibrarians)", e);
            return new CommandResult("/", true); //todo another redirect
        }
        req.setAttribute("users",librarians);
        return new CommandResult("/librarians.jsp");
    }
}
