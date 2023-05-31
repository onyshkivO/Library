package com.onyshkiv.command.impl.librarian;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiveBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GiveBookCommand.class);
    ActiveBookService activeBookService = ActiveBookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer activeBookId = Integer.valueOf(req.getParameter("id"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = req.getParameter("end_date");
        Date date;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            logger.error(String.format("bad date format %s", dateString));
            return new CommandResult("/controller?action=getOrders", true);
        }
        Double fine = Double.valueOf(req.getParameter("fine").replace(',', '.'));
        System.out.println(fine);
        try {
            activeBookService.updateActiveBookForGive(activeBookId, date, fine);
            logger.info(String.format("Active book %d was successfully given to user", activeBookId));
        } catch (ServiceException e) {
            logger.error("Problem with active Book service occurred!", e);
            return new CommandResult("/controller?action=getOrders", true);
        }
        return new CommandResult("/controller?action=getOrders", true);
    }
}
