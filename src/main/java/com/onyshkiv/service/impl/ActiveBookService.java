package com.onyshkiv.service.impl;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.EntityTransaction;
import com.onyshkiv.DAO.impl.ActiveBookDAO;
import com.onyshkiv.DAO.impl.BookDAO;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.IActiveBookService;
import com.onyshkiv.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Optional;
import java.util.Date;

public class ActiveBookService implements IActiveBookService {
    final static Logger logger = LogManager.getLogger(ActiveBookService.class);
    private ActiveBookDAO activeBookDAO;
    private BookService bookService;
    private BookDAO bookDAO;

    private EntityTransaction entityTransaction;
    private static ActiveBookService instance;


    public static synchronized ActiveBookService getInstance() {
        if (instance == null) {
            instance = new ActiveBookService();
        }
        return instance;
    }

    private ActiveBookService() {
        bookService = BookService.getInstance();
        activeBookDAO = ActiveBookDAO.getInstance();
        bookDAO = BookDAO.getInstance();
        entityTransaction = new EntityTransaction();
    }


    @Override
    public Optional<ActiveBook> findActiveBookById(Integer id) throws ServiceException {
        Optional<ActiveBook> optional;
        entityTransaction.init(activeBookDAO);
        try {
            optional = activeBookDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return optional;
    }

    @Override
    public Optional<ActiveBook> findActiveBookByUserAndBook(String login, String isbn) throws ServiceException {
        Optional<ActiveBook> optional;
        entityTransaction.init(activeBookDAO);
        try {
            optional = activeBookDAO.findActiveBookByUserAndBook(login, isbn);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return optional;
    }

    @Override
    public List<ActiveBook> findAllActiveBooks() throws ServiceException {
        List<ActiveBook> list;
        entityTransaction.init(activeBookDAO);
        try {
            list = activeBookDAO.findAll();
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return list;
    }

    @Override
    public List<ActiveBook> findActiveBooksOrders() throws ServiceException {
        List<ActiveBook> list;
        entityTransaction.init(activeBookDAO);
        try {
            list = activeBookDAO.findActiveBooksOrders();
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return list;
    }

    ;

    @Override
    public List<ActiveBook> findBooksByUserLogin(String login) throws ServiceException {
        List<ActiveBook> list;
        entityTransaction.init(activeBookDAO);
        try {
            list = activeBookDAO.findActiveBooksByUserLogin(login);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return list;
    }

    @Override
    public List<ActiveBook> findAllUsersActiveBooks() throws ServiceException {
        List<ActiveBook> list;
        entityTransaction.init(activeBookDAO);
        try {
            list = activeBookDAO.findAllUsersActiveBooks();
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return list;
    }

    @Override
    public void createActiveBook(ActiveBook activeBook) throws ServiceException {
        entityTransaction.init(activeBookDAO);
        try {
            if (bookService.isAvailableBook(activeBook.getBook().getIsbn())) {
                Book book = activeBook.getBook();
                book.setQuantity(book.getQuantity() - 1);
                bookService.updateBook(book);
                activeBookDAO.create(activeBook);
            } else throw new IllegalArgumentException("There are not available book");
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }

    @Override
    public void updateActiveBook(ActiveBook activeBook) throws ServiceException {
        entityTransaction.init(activeBookDAO);
        try {
            activeBookDAO.update(activeBook);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }

    @Override
    public void updateActiveBookForGive(Integer id, Date endDate, Double fine) throws ServiceException {
        entityTransaction.init(activeBookDAO);
        try {
            activeBookDAO.updateActiveBookForGive(id, endDate, fine);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }

    @Override
    public void updateActiveBookForGiveBack(Integer id) throws ServiceException {
        entityTransaction.initTransaction(activeBookDAO, bookDAO);
        try {
            ActiveBook activeBook = activeBookDAO.findEntityById(id).get();
            Optional<Book> bookOptional = bookDAO.findEntityById(activeBook.getBook().getIsbn());
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.setQuantity(book.getQuantity() + 1);
                bookDAO.update(book);
            }
            activeBookDAO.updateActiveBookForGiveBack(id);
            entityTransaction.commit();
        } catch (DAOException e) {
            //log
            entityTransaction.rollback();
            throw new ServiceException(e);
        } finally {
            entityTransaction.endTransaction(activeBookDAO, bookDAO);
        }
    }

    @Override
    public void deleteActiveBook(ActiveBook activeBook) throws ServiceException {
        entityTransaction.init(activeBookDAO);
        try {
            Book book = activeBook.getBook();
            book.setQuantity(book.getQuantity() + 1);
            bookService.updateBook(book);
            activeBookDAO.delete(activeBook);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }
}
