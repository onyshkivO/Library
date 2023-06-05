package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.User;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.UserService;
import com.onyshkiv.util.password.PasswordHashGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.onyshkiv.util.validation.Validation.validatePassword;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);
    UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String oldPassword = req.getParameter("password_old");
        String newPassword = req.getParameter("password_new");
        if (!validatePassword(newPassword)) {
            req.setAttribute("old", oldPassword);
            req.setAttribute("incorrect_password", true);
            logger.info("Invalid password to edit(#ChangePasswordCommand)");
            return new CommandResult("/change_password.jsp");
        }

        try {
            String passwordFromDB = userService.findUsePasswordByLogin(user.getLogin()).get();
            if (PasswordHashGenerator.verify(oldPassword, passwordFromDB)) {
                userService.changeUserPassword(PasswordHashGenerator.hash(newPassword), user.getLogin());
                logger.info("Password successfully changed(#ChangePasswordCommand)");
            } else {
                req.setAttribute("newPass", newPassword);
                logger.info("Password doesnt match(#ChangePasswordCommand)");
                req.setAttribute("bad_old_password", true);
                return new CommandResult("/change_password.jsp");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("Problem with user service occurred!(#ChangePasswordCommand)", e);
            return new CommandResult("/change_password.jsp");
        }
        return new CommandResult("/edit_profile.jsp", true);
    }
}
