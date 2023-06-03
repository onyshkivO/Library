package com.onyshkiv.service.impl;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.EntityTransaction;
import com.onyshkiv.DAO.impl.AuthorDAO;
import com.onyshkiv.DAO.impl.BookDAO;
import com.onyshkiv.DAO.impl.PublicationDAO;
import com.onyshkiv.entity.Author;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.IBookService;
import com.onyshkiv.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookService implements IBookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);
    private BookDAO bookDAO;
    private PublicationDAO publicationDAO;
    private AuthorDAO authorDAO;
    private EntityTransaction entityTransaction;

    private static BookService instance;

    public static synchronized BookService getInstance() {
        if (instance == null) {
            instance = new BookService();
        }
        return instance;
    }

    private BookService() {
        bookDAO = BookDAO.getInstance();
        authorDAO = AuthorDAO.getInstance();
        publicationDAO = PublicationDAO.getInstance();
        entityTransaction = new EntityTransaction();
    }

    @Override
    public Optional<Book> findBookById(String id) throws ServiceException {
        Optional<Book> optional;
        entityTransaction.init(bookDAO);
        try {
            optional = bookDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return optional;
    }
    @Override
    public Optional<Book> findEntityByIdAll(String id) throws ServiceException {
        Optional<Book> optional;
        entityTransaction.init(bookDAO);
        try {
            optional = bookDAO.findEntityByIdAll(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return optional;
    }

    @Override
    public List<Book> findAllVailableBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException {
        List<Book> list;
        entityTransaction.init(bookDAO);
        try {
            list = bookDAO.findAllVailableBooksByOption(name,false,sortOption,orderOption,booksPerPage,offset);
        } catch (DAOException e) {
            logger.error("Problem with DAO occurred!", e);
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return list;
    }
    @Override
    public List<Book> findAllVailableBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException {
        List<Book> list;
        entityTransaction.init(bookDAO);
        try {
            list = bookDAO.findAllVailableBooksByOption(name,true,sortOption,orderOption,booksPerPage,offset);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return list;
    }
    @Override
    public List<Book> findAllBooksByName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException {
        List<Book> list;
        entityTransaction.init(bookDAO);
        try {
            list = bookDAO.findAllBooksByOption(name,false,sortOption,orderOption,booksPerPage,offset);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return list;
    }
    @Override
    public List<Book> findAllBooksByAuthorName(String name, String sortOption, String orderOption,Integer booksPerPage,Integer offset) throws ServiceException {
        List<Book> list;
        entityTransaction.init(bookDAO);
        try {
            list = bookDAO.findAllBooksByOption(name,true,sortOption,orderOption,booksPerPage,offset);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return list;
    }

    @Override
    public Integer findNumberOfAllVailableBooksByName(String name) throws ServiceException {
        Integer res;
        entityTransaction.init(bookDAO);
        try {
            res = bookDAO.getNumberOfAvailableBooksByOption(name,false);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return res;
    }

    @Override
    public Integer findNumberOfAllVailableBooksByAuthorName(String name) throws ServiceException {
        Integer res;
        entityTransaction.init(bookDAO);
        try {
            res = bookDAO.getNumberOfAvailableBooksByOption(name,true);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return res;
    }

    @Override
    public Integer findNumberOfAllBooksByName(String name) throws ServiceException {
        Integer res;
        entityTransaction.init(bookDAO);
        try {
            res = bookDAO.getNumberOfBooksByOption(name,false);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return res;
    }

    @Override
    public Integer findNumberOfAllBooksByAuthorName(String name) throws ServiceException {
        Integer res;
        entityTransaction.init(bookDAO);
        try {
            res = bookDAO.getNumberOfBooksByOption(name,true);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return res;
    }

    @Override
    public void createBook(Book book) throws ServiceException {
        entityTransaction.initTransaction(bookDAO, authorDAO);
        try {
            bookDAO.create(book);
            for (Author a : book.getAuthors()) {
                authorDAO.setAuthorBookTableConnection(book.getIsbn(), a.getAuthorId());
            }
            entityTransaction.commit();
        } catch (DAOException e) {
            entityTransaction.rollback();
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.endTransaction(bookDAO, authorDAO);
        }

    }

    @Override
    public void updateBook(Book book) throws ServiceException {
        entityTransaction.initTransaction(bookDAO, authorDAO);
        try {
            bookDAO.update(book);
            authorDAO.removeAuthorBookTableConnection(book.getIsbn());
            for (Author a : book.getAuthors()) {
                authorDAO.setAuthorBookTableConnection(book.getIsbn(), a.getAuthorId());
            }
            entityTransaction.commit();
        } catch (DAOException e) {
            //log
            entityTransaction.rollback();
            throw new ServiceException(e);
        } finally {
            entityTransaction.endTransaction(bookDAO, authorDAO);
        }
    }

    @Override
    public void deleteBook(Book book) throws ServiceException {
        entityTransaction.initTransaction(bookDAO, authorDAO);
        try {
            bookDAO.delete(book);
            authorDAO.removeAuthorBookTableConnection(book.getIsbn());
            entityTransaction.commit();
        } catch (DAOException e) {
            //log
            entityTransaction.rollback();
            throw new ServiceException(e);
        } finally {
            entityTransaction.endTransaction(bookDAO, authorDAO);
        }
    }

    @Override
    public boolean isAvailableBook(String isbn) throws ServiceException {
        entityTransaction.init(bookDAO);
        try {
            Optional<Book> book = bookDAO.findEntityById(isbn);
            if (book.isPresent() && book.get().getQuantity() > 0) return true;
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(bookDAO);
        }
        return false;
    }
    @Override
    public void hideBook(String isbn) throws ServiceException {
        entityTransaction.initTransaction(bookDAO, authorDAO);
        try {
            bookDAO.hide(isbn);
            authorDAO.removeAuthorBookTableConnection(isbn);
            entityTransaction.commit();
        } catch (DAOException e) {
            //log
            entityTransaction.rollback();
            throw new ServiceException(e);
        } finally {
            entityTransaction.endTransaction(bookDAO, authorDAO);
        }

    }
}
