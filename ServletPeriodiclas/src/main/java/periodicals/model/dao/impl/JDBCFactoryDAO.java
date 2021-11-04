package periodicals.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.filter.AuthFilter;
import periodicals.exception.DataBaseException;
import periodicals.model.connection.ConnectionDB;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.ReplenishmentDAO;
import periodicals.model.dao.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCFactoryDAO extends FactoryDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCFactoryDAO.class.getName());

    private DataSource dataSource;

    public JDBCFactoryDAO(){
        this(ConnectionDB.getDataSource());
    }

    public JDBCFactoryDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }


    private Connection getConnection(){
        try{
            return dataSource.getConnection();
        } catch (SQLException ex){
            LOGGER.error("{}: {}", DataBaseException.class.getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    @Override
    public UserDAO createUserDAO() {
        return new JDBCUserDAO(getConnection());
    }

    public PeriodicalDAO createPeriodicalDAO(){
        return new JDBCPeriodicalDAO(getConnection());
    }
    public ReplenishmentDAO createReplenishmentDAO(){
        return new JDBCReplenishmentDAO(getConnection());
    }
}
