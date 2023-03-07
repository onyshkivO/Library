package com.onyshkiv.service.impl;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.EntityTransaction;
import com.onyshkiv.DAO.impl.ActiveBookDAO;
import com.onyshkiv.entity.ActiveBook;
import com.onyshkiv.entity.Book;
import com.onyshkiv.service.IActiveBookService;
import com.onyshkiv.service.ServiceExcpetion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ActiveBookService implements IActiveBookService {
    final static Logger logger = LogManager.getLogger(ActiveBookService.class);
    private ActiveBookDAO activeBookDAO;
    private BookService bookService;

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
        entityTransaction = new EntityTransaction();
    }


    @Override
    public Optional<ActiveBook> findActiveBookById(Integer id) throws ServiceExcpetion {
        Optional<ActiveBook> optional;
        entityTransaction.init(activeBookDAO);
        try {
            optional = activeBookDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceExcpetion(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return optional;
    }

    @Override
    public List<ActiveBook> findAllActiveBooks() throws ServiceExcpetion {
        List<ActiveBook> list;
        entityTransaction.init(activeBookDAO);
        try {
            list = activeBookDAO.findAll();
        } catch (DAOException e) {
            //log
            throw new ServiceExcpetion(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
        return list;
    }

    @Override
    public void createActiveBook(ActiveBook activeBook) throws ServiceExcpetion {
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
            throw new ServiceExcpetion(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }

    @Override
    public void updateActiveBook(ActiveBook activeBook) throws ServiceExcpetion {
        entityTransaction.init(activeBookDAO);
        try {
            activeBookDAO.update(activeBook);
        } catch (DAOException e) {
            //log
            throw new ServiceExcpetion(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }

    @Override
    public void deleteActiveBook(ActiveBook activeBook) throws ServiceExcpetion {
        entityTransaction.init(activeBookDAO);
        try {
            Book book = activeBook.getBook();
            book.setQuantity(book.getQuantity() +1);
            bookService.updateBook(book);
            activeBookDAO.delete(activeBook);
        } catch (DAOException e) {
            //log
            throw new ServiceExcpetion(e);
        } finally {
            entityTransaction.end(activeBookDAO);
        }
    }
}
