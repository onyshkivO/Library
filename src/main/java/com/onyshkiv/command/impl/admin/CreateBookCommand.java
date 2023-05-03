package com.onyshkiv.command.impl.admin;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.command.impl.reader.AddBookCommand;
import com.onyshkiv.entity.*;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.ActiveBookService;
import com.onyshkiv.service.impl.AuthorService;
import com.onyshkiv.service.impl.BookService;
import com.onyshkiv.service.impl.PublicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.onyshkiv.util.validation.Validation.*;

public class CreateBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateBookCommand.class);
    BookService bookService = BookService.getInstance();
    AuthorService authorService = AuthorService.getInstance();
    PublicationService publicationService = PublicationService.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {

//        boolean flag = false;

//        String login = req.getParameter("login");
//        req.setAttribute("login", login);
//        if (!validateLogin(login)) {
//            req.removeAttribute("login");
//            req.setAttribute("incorrect_login", true);
//            flag = true;
//        }
//
//        String email = req.getParameter("email");
//        req.setAttribute("email", email);
//        if (!validateEmail(email)) {
//            req.removeAttribute("email");
//            req.setAttribute("incorrect_Email", true);
//            flag = true;
//        }
//
//        String firstName = req.getParameter("first_name");
//        req.setAttribute("first_name", firstName);
//        if (!validateName(firstName)) {
//            req.removeAttribute("first_name");
//            req.setAttribute("incorrect_firstName", true);
//            flag = true;
//        }
//
//        String lastName = req.getParameter("last_name");
//        req.setAttribute("last_name", lastName);
//        if (!validateName(lastName)) {
//            req.removeAttribute("last_name");
//            req.setAttribute("incorrect_lastName", true);
//            flag = true;
//        }
//        String phone = req.getParameter("phone");
//        req.setAttribute("phone", phone);
//        if (!validatePhone(phone)) {
//            phone = null;
//        }
//
//        String pass = req.getParameter("password");
//        req.setAttribute("password", pass);
//        if (pass.length() < 3) {
//            req.removeAttribute("password");
//            req.setAttribute("incorrect_password", true);
//            flag = true;
//        }
//
//
//        if (flag)
//            return new CommandResult(page);


        Integer isbn = Integer.valueOf(req.getParameter("isbn"));
        String name = req. getParameter("name");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String radioButton = req.getParameter("inlineRadioOptions");
            Publication publication;
            if (radioButton.equals("option1")) {
                Integer publicationId = Integer.valueOf(req.getParameter("publication"));
                publication = publicationService.findPublicationById(publicationId).get();
            } else {
                String publicationName = req.getParameter("publication_name");
                publication = new Publication(publicationName);
                publicationService.createPublication(publication);
            }
            String[] selectedAuthors = req.getParameterValues("authors");
            Set<Author> authors = new HashSet<>();
            for(String s : selectedAuthors){
                authors.add(authorService.findAuthorById(Integer.valueOf(s)).get());
            }
            Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            String yearOfPublicationString = req.getParameter("year_of_publication");

            Date date;
            try {
                date = formatter.parse(yearOfPublicationString);
            }catch (ParseException e){
                //log
                req.setAttribute("bad_date_format",true);
                return new CommandResult("/controller?action=AddBookPage");
            }

            String details = req.getParameter("details");

            Book book = new Book(isbn,name,date,publication,quantity,details,authors);
            bookService.createBook(book);
//            System.out.println();
//            System.out.println("isbn: "+book.getIsbn());
//            System.out.println("name: "+book.getName());
//            System.out.println("date: "+book.getDateOfPublication());
//            System.out.println("publication: "+book.getPublication().getPublicationId()+" name "+book.getPublication().getPublicationId());
//            System.out.println("quantity: "+book.getQuantity());
//            System.out.println("details: "+book.getDetails());
//            System.out.println("authors: "+book.getAuthors());

        }catch (ServiceException e ){
            //log
        }


        return new CommandResult("/controller?action=AddBookPage",true);
    }
}
