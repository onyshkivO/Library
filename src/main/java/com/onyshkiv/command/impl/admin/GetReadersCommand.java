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

public class GetReadersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetReadersCommand.class);
    UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> readers;
        try {
            readers = userService.findReaders();
        } catch (ServiceException e) {
            logger.error("Problem with user service occurred!(#GetReadersCommand)", e);
            return new CommandResult("/", true);
        }
        req.setAttribute("users", readers);
        logger.info("Admin successfully get readers page");
        return new CommandResult("/readers.jsp");
    }
}
