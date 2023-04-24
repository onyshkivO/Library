package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.*;
import com.onyshkiv.service.ServiceExcpetion;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddBookCommand implements Command {
    ActiveBookService activeBookService = ActiveBookService.getInstance();
    BookService bookService = BookService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("user");
        Integer isbn = Integer.valueOf( req.getParameter(  "isbn"));


        try {
            Book book = bookService.findBookById(isbn).get();

            if(activeBookService.findActiveBookByUserAndBook(user.getLogin(),isbn).isPresent()){
                System.out.println("user already has a book ");
                return new CommandResult("/controller?action=bookPage&already=true",true);
            }



            ActiveBook activeBook= new ActiveBook(book,user,new SubscriptionStatus(4), new Date(),new Date(),null);
            activeBookService.createActiveBook(activeBook);
            //List<Book> books = (List<Book>) session.getAttribute("user_books");
            //if (books==null) books = new ArrayList<>();
            //books.add(book);
            //session.setAttribute("user_books",books);
        } catch (ServiceExcpetion e) {
            e.printStackTrace();
            //log
            System.out.println("something went wronge");
            return new CommandResult("/",true);

        }
//        req.setAttribute("active",true);
        //todo rpg pattern
        return new CommandResult("/controller?action=bookPage&isbn="+isbn,true);
    }
}
