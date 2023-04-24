package com.onyshkiv.service.impl;

import com.onyshkiv.DAO.DAOException;
import com.onyshkiv.DAO.EntityTransaction;
import com.onyshkiv.DAO.impl.PublicationDAO;
import com.onyshkiv.entity.Publication;
import com.onyshkiv.service.IPublicationService;
import com.onyshkiv.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PublicationService implements IPublicationService {
    final static Logger logger = LogManager.getLogger(PublicationService.class);
    private final PublicationDAO publicationDAO;
    private final EntityTransaction entityTransaction;
    private static PublicationService instance;

    public static synchronized PublicationService getInstance() {
        if (instance == null) {
            instance = new PublicationService();
        }
        return instance;
    }

    private PublicationService() {
        publicationDAO = PublicationDAO.getInstance();
        entityTransaction = new EntityTransaction();
    }


    @Override
    public List<Publication> findAllPublication() throws ServiceException {
        List<Publication> list;

        entityTransaction.init(publicationDAO);
        try {
            list = publicationDAO.findAll();
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
        return list;
    }

    @Override
    public Optional<Publication> findPublicationById(Integer id) throws ServiceException {
        Optional<Publication> optional;

        entityTransaction.init(publicationDAO);
        try {
            optional = publicationDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
        return optional;
    }

    @Override
    public void createPublication(Publication publication) throws ServiceException {

        entityTransaction.init(publicationDAO);
        try {
            publicationDAO.create(publication);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
    }

    @Override
    public void updatePublication(Publication publication) throws ServiceException {

        entityTransaction.init(publicationDAO);
        try {
            publicationDAO.update(publication);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
    }

    @Override
    public void deletePublication(Publication publication) throws ServiceException {

        entityTransaction.init(publicationDAO);
        try {
            publicationDAO.delete(publication);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
    }

    @Override
    public boolean containsPublication(Integer id) throws ServiceException {
        Optional<Publication> optional;

        entityTransaction.init(publicationDAO);
        try {
            optional = publicationDAO.findEntityById(id);
        } catch (DAOException e) {
            //log
            throw new ServiceException(e);
        } finally {
            entityTransaction.end(publicationDAO);
        }
        return optional.isPresent();
    }
}
