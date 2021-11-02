package periodicals.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.filter.AuthFilter;
import periodicals.dto.Page;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.mapper.PeriodicalMapper;
import periodicals.model.dao.mapper.UserMapper;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.util.property.DBProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

    private Connection connection;
    private DBProperty statements;

    public JDBCUserDAO(Connection connection){
        this.connection = connection;
        this.statements = DBProperty.getDbProperty();
    }

    @Override
    public Optional<User> findByEmail(String email)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.find.by.email"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setString(1, email);
            try(ResultSet res = statement.executeQuery()){
                if(res.next()){
                    Optional<User> user = Optional.of(UserMapper.extractFromResultSet(res));
                    res.last();
                    user.get().setPeriodicals(PeriodicalMapper.getPeriodicalSet(res, res.getRow()));
                    return user;
                }
                LOGGER.info("ResultSet is empty");
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<User> findRedaerById(Long id)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.find.reader.by.id"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setLong(1, id);
            try(ResultSet res = statement.executeQuery()){
                if(res.next()){
                    return Optional.of(UserMapper.extractFromResultSet(res));
                }
                LOGGER.info("ResultSet is empty");
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<User> findByRole(Role role, Pageable pageable)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.find.by.role"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
           UserMapper.setFindByRolePrepearedStatement(role,
                   pageable.getLimit(),
                   pageable.getOffset(),
                   statement);
           try(ResultSet res = statement.executeQuery()){
                if(res.next()){
                    return new Page(UserMapper.getUserSet(res, pageable.getLimit()),
                            (pageable.getOffset()/pageable.getLimit())+1);
                }
                LOGGER.info("ResultSet is empty");
                throw new SQLException();
           }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<User> findByEmail(String email, Pageable pageable)throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("user.find.by.email"))){
            statement.setString(1, email);
            try(ResultSet res = statement.executeQuery()){
                LOGGER.info("RESULT SET: {}", res);
                if(res.next()){
                    return new Page(UserMapper.getUserSet(res, pageable.getLimit()),
                            (pageable.getOffset()/pageable.getLimit())+1);
                }
                LOGGER.info("ResultSet is empty");
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public User save(User entity) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(
                statements.getProperty(Objects.isNull(entity.getId())? "user.insert" : "user.update"))){
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
