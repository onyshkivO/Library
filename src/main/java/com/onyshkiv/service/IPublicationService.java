package com.onyshkiv.service;

import com.onyshkiv.entity.Publication;

import java.util.List;
import java.util.Optional;

public interface IPublicationService {
    List<Publication> findAllPublication() throws ServiceExcpetion;
    Optional<Publication> findPublicationById(Integer id) throws ServiceExcpetion;
    void createPublication(Publication publication) throws ServiceExcpetion;
    void updatePublication(Publication publication) throws ServiceExcpetion;
    void deletePublication(Publication publication) throws ServiceExcpetion;
    boolean containsPublication(Integer id) throws ServiceExcpetion;
}
