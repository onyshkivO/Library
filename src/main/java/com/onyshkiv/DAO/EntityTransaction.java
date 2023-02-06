package com.onyshkiv.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(EntityTransaction.class);

    public void initTransaction(AbstractDAO dao, AbstractDAO... daos) {
        try {
            if (connection == null) {
                connection = DataSource.getConnection();
            }
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            //log
        }
        dao.setConnection(connection);
        for (AbstractDAO d : daos) {
            d.setConnection(connection);
        }
    }

    public void endTransaction(AbstractDAO dao, AbstractDAO... daos) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException e) {
                //log
            }

            try {
                connection.close();
            } catch (SQLException e) {
                //log
            }
            connection = null;
        }
    }

    public void init(AbstractDAO dao) {
        try {
            if (connection == null) {
                connection = DataSource.getConnection();
            }
        }catch (SQLException e){
            //log
        }
        dao.setConnection(connection);
    }

    public void end(AbstractDAO dao){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        connection=null;
    }

    public void commit(){
        try{
            connection.commit();
        }catch(SQLException e){
            //log
        }
    }

    public void rollback(){
        try{
            connection.rollback();
        }catch(SQLException e){
            //log
        }
    }

}
