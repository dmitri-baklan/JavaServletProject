package periodicals.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.mapper.PeriodicalMapper;
import periodicals.model.dao.mapper.UserMapper;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;
import periodicals.util.DBProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

public class JDBCPeriodicalDAO implements PeriodicalDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCPeriodicalDAO.class.getName());

    private Connection connection;
    private DBProperty statements;

    public JDBCPeriodicalDAO(Connection connection){
        this.connection = connection;
        this.statements = DBProperty.getDbProperty();
    }

    @Override
    public Periodical save(Periodical entity) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(
                statements.getProperty(Objects.isNull(entity.getId())? "periodical.insert" : "periodical.update"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            PeriodicalMapper.setPeriodicalPreparedStatement(entity, statement);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    entity.setId(res.getLong("p_id"));
                    LOGGER.info("Periodical saved successfully, id[{}]", entity.getId());
                    return entity;
                }
                LOGGER.info("Periodical not saved");
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<Periodical> findById(Long id) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("periodical.find.by.id"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setLong(1, id);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    Optional<Periodical> periodical = Optional.of(PeriodicalMapper.extractFromResultSet(res));
                    res.last();
                    periodical.get().setUsers(UserMapper.getUserSet(res, res.getRow()));
                    return periodical;
                }
                LOGGER.info("ResultSet is empty");
                return Optional.empty();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<Periodical> findAll(Pageable pageable)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("periodical.find.all"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            PeriodicalMapper.setFindAllPreperadStatement(statement,pageable);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    return new Page(PeriodicalMapper.getPeriodicalSet(res, pageable.getLimit()),
                            (pageable.getOffset()/pageable.getLimit())+1);
                }
                LOGGER.info("ResultSet is empty");
                return new Page(new HashSet<Periodical>(),
                        (pageable.getOffset()/pageable.getLimit())+1);
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<Periodical> findByName(String searchQuery)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("periodical.find.by.name"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setString(1, searchQuery);
            try(ResultSet res = statement.executeQuery()){
                LOGGER.info("RESULT SET: {}", res);
                if(res.first()){
                    return new Page<>(PeriodicalMapper.getPeriodicalSet(res, 2), 0);
                }
                LOGGER.info("ResultSet is empty");
                return new Page<>(new HashSet<>(), 0);
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<Periodical> findBySubject(Subject subj, Pageable pageable)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("periodical.filter.by.subject"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            PeriodicalMapper.setFindAllPreperadStatement(statement,pageable);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    return new Page<>(PeriodicalMapper.getPeriodicalSet(res, pageable.getLimit()),
                            (pageable.getOffset()/pageable.getLimit())+1);
                }
                LOGGER.info("ResultSet is empty");
                return new Page<>(new HashSet<Periodical>(),
                        (pageable.getOffset()/pageable.getLimit())+1);
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void deleteById(Long id)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("periodical.delete"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setLong(1, id);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    return;
                }
                LOGGER.info("ResultSet is empty");
                return;
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOGGER.error("{}: {}",ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }
}
