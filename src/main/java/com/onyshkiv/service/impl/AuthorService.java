package com.onyshkiv.service.impl;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.EntityTransaction;
import com.onyshkiv.DAO.impl.AuthorDAO;
import com.onyshkiv.entity.Author;
import com.onyshkiv.service.IAuthorService;
import com.onyshkiv.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class AuthorService implements IAuthorService {
    final static Logger logger = LogManager.getLogger(AuthorService.class);
    private AuthorDAO authorDAO;
    private EntityTransaction entityTransaction;
    private static AuthorService instance;


    public static synchronized AuthorService getInstance() {
        if (instance == null) {
            instance = new AuthorService();
        }
        return instance;
    }

    private AuthorService() {
        authorDAO = AuthorDAO.getInstance();
        entityTransaction = new EntityTransaction();
    }



    @Override
    public List<Author> findAllAuthors() throws ServiceException {
        List<Author> list;

        entityTransaction.init(authorDAO);
        try {
            list = authorDAO.findAll();
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(authorDAO);
        }
        return list;

    }

    @Override
    public Optional<Author> findAuthorById(Integer id) throws ServiceException {
        Optional<Author> optional;
        entityTransaction.init(authorDAO);
        try {
            optional = authorDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(authorDAO);
        }
        return optional;
    }

    @Override
    public void createAuthor(Author author) throws ServiceException {
        entityTransaction.init(authorDAO);
        try{
            authorDAO.create(author);
        }catch (DAOException e){
            //log
            throw new ServiceException(e);
        }finally {
            entityTransaction.end(authorDAO);
        }
    }

    @Override
    public void updateAuthor(Author author) throws ServiceException {
        entityTransaction.init(authorDAO);

        try{
            authorDAO.update(author);
        }catch (DAOException e){
            //log
            throw new ServiceException(e);
        }finally {
            entityTransaction.end(authorDAO);
        }
    }

    @Override
    public void deleteAuthor(Author author) throws ServiceException {
        entityTransaction.init(authorDAO);

        try{
            authorDAO.delete(author);
        }catch (DAOException e){
            //log
            throw new ServiceException(e);
        }finally {
            entityTransaction.end(authorDAO);
        }
    }
}
