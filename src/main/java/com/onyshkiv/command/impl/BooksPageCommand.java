package com.onyshkiv.command.impl;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandResult;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.ServiceException;
import com.onyshkiv.service.impl.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public class BooksPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BooksPageCommand.class);
    BookService bookService = BookService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Book> books;
        String name = req.getParameter("name");
        String searchOption = req.getParameter("search_option");
        String sortOption = req.getParameter("sort_option");
        String sortOptionOrder = req.getParameter("sort_option_order");
        System.out.println("SearchOption " + searchOption);
        System.out.println("sortOption " + sortOption);
        System.out.println("sortOptionOrder " + sortOptionOrder);
        System.out.println(name);


        if (name != null && !name.equals("") && searchOption.equals("book_name")) {
            try {
                books = bookService.findAllVailableBooksByName(name);
            } catch (ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage", true);
            }
        } else if (name != null && !name.equals("") && searchOption.equals("author_name")) {
            try {
                books = bookService.findAllVailableBooksByAuthorName(name);
            } catch (ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage", true);
            }
        } else {
            try {
                books = bookService.findAllBooks();
            } catch (ServiceException e) {
                logger.error("Problem with service occurred!", e);
                return new CommandResult("/controller?action=bookPage", true);
            }
        }
        books.forEach(book -> book.getAuthors().stream().sorted(Comparator.comparing(Author::getName)));
        if (sortOption == null) books.sort(Comparator.comparing(Book::getName));
        else
            switch (sortOption) {
                case "book_name":
                    System.out.println(sortOptionOrder.equals("asc"));
                    if (sortOptionOrder.equals("asc"))
                        books.sort(Comparator.comparing(Book::getName));
                    else books.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                    break;
                case "author_name":
                    books.sort((o1, o2) -> {


                        Iterator<Author> firstAuthors = o1.getAuthors().iterator();
                        Iterator<Author> secondAuthors = o2.getAuthors().iterator();
                        while (firstAuthors.hasNext() && secondAuthors.hasNext()) {
                            int comp = 0;
                            if (sortOptionOrder.equals("asc"))
                                comp = firstAuthors.next().getName().compareTo(secondAuthors.next().getName());
                            else
                                comp = secondAuthors.next().getName().compareTo(firstAuthors.next().getName());
                            if (comp != 0) {
                                return comp;
                            }
                        }
                        if (sortOptionOrder.equals("asc")){
                            return firstAuthors.hasNext()?1:-1;
                        }
                        return secondAuthors.hasNext()?1:-1;



//
//                        StringBuilder stringBuilder1 = new StringBuilder();
//                        StringBuilder stringBuilder2 = new StringBuilder();
//
//                        while (firstAuthors.hasNext()) {
//                            stringBuilder1.append(firstAuthors.next());
//                        }
//                        while (secondAuthors.hasNext()) {
//                            stringBuilder2.append(secondAuthors.next());
//                        }
//
//                        return sortOptionOrder.equals("asc") ? stringBuilder1.toString().compareTo(stringBuilder2.toString()) :
//                                stringBuilder2.toString().compareTo(stringBuilder1.toString());


                    });//todo norm sorting
                    break;
                case "date_of_publication":
                    if (sortOptionOrder.equals("asc"))
                        books.sort(Comparator.comparing(Book::getDateOfPublication));
                    else books.sort((o1, o2) -> o2.getDateOfPublication().compareTo(o1.getDateOfPublication()));
                    break;
                case "publication_name":
                    if (sortOptionOrder.equals("asc"))
                        books.sort(Comparator.comparing(o -> o.getPublication().getName()));
                    else books.sort((o1, o2) -> o2.getPublication().getName().compareTo(o1.getPublication().getName()));
                    break;

            }

        req.setAttribute("books", books);
        req.setAttribute("search_option", searchOption);
        req.setAttribute("sort_option", sortOption);
        req.setAttribute("sort_option_order", sortOptionOrder);
        req.setAttribute("name", name);

        return new CommandResult("/books.jsp");
    }
}
