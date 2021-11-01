package periodicals.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.filter.AuthFilter;
import periodicals.dto.Page;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.mapper.UserMapper;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.util.property.DBProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

    private Connection connection;
    private DBProperty statements;

    public JDBCUserDAO(Connection connection){
        this.connection = connection;
        this.statements = DBProperty.getDbProperty();
    }
//
//    public JDBCUserDAO() {
//        super();
//    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findRedaerById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<User> findByRole(Role role, Pageable pageable) {
        return null;
    }

    @Override
    public Page<User> findByEmail(String email, Pageable pageable)throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.find.by.email"))){
            statement.setString(1, email);
            try(ResultSet res = statement.executeQuery()){
                if(res.next()){
                    //return Optional.of(UserMapper.getUserFromResultSet(res));
                    return UserMapper.getUserPage(res, pageable.getLimit(), pageable.getOffset());
                }
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public User save(User entity) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.insert"))){
            UserMapper.setUserPreparedStatement(entity, statement);
            try(ResultSet res = statement.executeQuery()){
                if(res.next()){
                    entity.setId(res.getLong("id"));
                    return entity;
                }
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOGGER.error("{}: {}",ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }
}
