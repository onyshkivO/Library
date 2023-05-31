package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.SubscriptionStatus;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GiveBookBackCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GiveBookBackCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer activeBookId = Integer.valueOf(req.getParameter("active_book_id"));
        try {
            Optional<ActiveBook> activeBookOptional = activeBookService.findActiveBookById(activeBookId);
            if (activeBookOptional.isEmpty()) {
                logger.info("There are not available active book, bad input parameter active book id(Optional = null)");
                return new CommandResult("/controller?action=getUsersBook", true);
            }
            ActiveBook activeBook = activeBookOptional.get();
            activeBook.setSubscriptionStatus(new SubscriptionStatus(2));
            activeBookService.updateActiveBookForGiveBack(activeBook.getActiveBookId());
            logger.info(String.format("active book with id %d was successfully returned",activeBookId));
        } catch (ServiceException e) {
            logger.error("Problem with active book service occurred!", e);
            return new CommandResult("/controller?action=getUsersBook", true);
        }
        return new CommandResult("/controller?action=getUsersBook", true);
    }
}
