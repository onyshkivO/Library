package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiveBookCommand implements Command {
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Integer activeBookId = Integer.valueOf(req.getParameter("id"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = req.getParameter("end_date");
        Date date;
        System.out.println(dateString);
        try {
           date = formatter.parse(dateString);
        }catch (ParseException e){
            //log
            req.setAttribute("bad_date_format",true);
            return new CommandResult("/controller?action=getOrders");
        }
        Double fine = Double.valueOf(req.getParameter("fine"));
        try {
            activeBookService.updateActiveBookForGive(activeBookId, date, fine);
        }catch (ServiceException e){
            //log
            e.printStackTrace();
            req.setAttribute("wronge",true);
            return new CommandResult("/controller?action=getOrders");
        }
        //todo maybe something wronge there(application/x-www-form-urlencoded)
        return new CommandResult("/controller?action=getOrders",true);
    }
}
