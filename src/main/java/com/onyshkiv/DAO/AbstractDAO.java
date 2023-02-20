package com.onyshkiv.DAO;

import com.onyshkiv.entity.Entity;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<K, T extends Entity> {
    protected Connection con;
    public abstract List<T> findAll() throws DAOException;
    public abstract Optional<T> findEntityById(K id) throws DAOException;
    public abstract void create(T model) throws DAOException;
    public abstract void update(T model) throws DAOException;
    public abstract void delete(T model) throws DAOException;

    public void setConnection(Connection connection){
        this.con=connection;
    }




}
