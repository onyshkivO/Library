package com.onyshkiv.command.impl.admin.manage_books;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.ServiceException;
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

public class EditBookCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditBookCommand.class);
    BookService bookService = BookService.getInstance();
    AuthorService authorService = AuthorService.getInstance();
    PublicationService publicationService = PublicationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        boolean flag = false;
        HttpSession session = req.getSession();
        String isbn = req.getParameter("isbn");
        session.setAttribute("isbn", isbn);

        String name = req.getParameter("name");
        session.setAttribute("name", name);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Publication publication;
            Integer publicationId = Integer.valueOf(req.getParameter("publication"));
            publication = publicationService.findPublicationById(publicationId).get();
            session.setAttribute("publication_id", publication.getPublicationId());

            String[] selectedAuthors = req.getParameterValues("authors");
            List<String> list = Arrays.asList(selectedAuthors);
            Set<Author> authors = new HashSet<>();
            session.setAttribute("selected_authors", list);

            for (String s : selectedAuthors) {
                authors.add(authorService.findAuthorById(Integer.valueOf(s)).get());
            }
            session.setAttribute("selected_authors_real", authors);
            Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            session.setAttribute("quantity", quantity);
            String yearOfPublicationString = req.getParameter("year_of_publication");
            session.setAttribute("year_of_publication", yearOfPublicationString);
            Date date;
            String details = req.getParameter("details");
            session.setAttribute("details", details);
            try {
                date = formatter.parse(yearOfPublicationString);
            } catch (ParseException e) {
                //log
                req.setAttribute("bad_date_format", true);
                return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);
            }
            session.setAttribute("date", date);

            if (flag)
                return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);
            Book book = new Book(isbn, name, date, publication, quantity, details, authors);
            bookService.updateBook(book);

        } catch (ServiceException e) {
            //log
            session.setAttribute("already_exist_isbn", true);

//            return new CommandResult("/controller?action=AddBookPage",true);
            return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);//todo може щось не так, адже і так ми isbn пишемо в сесію
        }
        session.removeAttribute("isbn");
        session.removeAttribute("incorrect_isbn");
        session.removeAttribute("name");
        session.removeAttribute("publication_id");
        session.removeAttribute("selected_authors");
        session.removeAttribute("quantity");
        session.removeAttribute("year_of_publication");
        session.removeAttribute("details");
        session.removeAttribute("selected_authors_real");

        return new CommandResult("/controller?action=bookPage", true);
    }
}
