package com.onyshkiv.service;

import com.onyshkiv.entity.Publication;

import java.util.List;
import java.util.Optional;

public interface IPublicationService {
    List<Publication> findAllPublication() throws ServiceException;
    Optional<Publication> findPublicationById(Integer id) throws ServiceException;
    void createPublication(Publication publication) throws ServiceException;
    void updatePublication(Publication publication) throws ServiceException;
    void deletePublication(Publication publication) throws ServiceException;
    boolean containsPublication(Integer id) throws ServiceException;
}
