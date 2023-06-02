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
        isbn = isbn.replace("-", "");
        session.setAttribute("isbn", isbn);

        String name = req.getParameter("name");
        session.setAttribute("name", name);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Publication publication = null;
            Integer publicationId = Integer.valueOf(req.getParameter("publication"));
            Optional<Publication> optionalPublication = publicationService.findPublicationById(publicationId);
            if (optionalPublication.isEmpty()) {
                logger.info(String.format("There are not publication with id %d", publicationId));
                flag = true;
            } else {
                publication = optionalPublication.get();
                session.setAttribute("publication_id", publication.getPublicationId());
            }
            String[] selectedAuthors = req.getParameterValues("authors");
            List<String> list = Arrays.asList(selectedAuthors);
            Set<Author> authors = new HashSet<>();
            session.setAttribute("selected_authors", list);
            Optional<Author> optionalAuthor;
            for (String s : selectedAuthors) {
                optionalAuthor = authorService.findAuthorById(Integer.valueOf(s));
                if (optionalAuthor.isEmpty()) continue;
                authors.add(optionalAuthor.get());
            }
            if (authors.isEmpty()) {
                logger.info(String.format("There are not authors with ids %s", Arrays.toString(selectedAuthors)));
                flag = true;
            } else {
                session.setAttribute("selected_authors_real", authors);
            }
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            session.setAttribute("quantity", quantity);

            String yearOfPublicationString = req.getParameter("year_of_publication");
            session.setAttribute("year_of_publication", yearOfPublicationString);

            Date date = null;
            try {
                date = formatter.parse(yearOfPublicationString);
                session.setAttribute("date", date);
            } catch (ParseException e) {
                logger.error(String.format("bad date format %s", yearOfPublicationString));
                session.removeAttribute("date");
                flag = false;
            }


            String details = req.getParameter("details");
            session.setAttribute("details", details);
            if (flag) {
                logger.info("Invalid data to update book");
                return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);
            }

            if (bookService.findBookById(isbn).isEmpty()) {
                logger.info("ISBN doesnt  exist");
                session.setAttribute("doesnt_exist_isbn", true);
                return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);
            }


            Book book = new Book(isbn, name, date, publication, quantity, details, authors);
            bookService.updateBook(book);

        } catch (ServiceException e) {
            logger.error("Problem with book publication author service occurred!", e);

            return new CommandResult(String.format("/controller?action=editBookPage&isbn=%s", isbn), true);
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
        logger.info(String.format("Admin successfully updated books with isbn %s", isbn));
        return new CommandResult("/controller?action=bookPage", true);
    }
}
