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
import java.util.Set;

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
        try(PreparedStatement statement = connection.prepareStatement(
                String.format(statements.getProperty("periodical.find.all"),
                        pageable.getSortField(),pageable.isAscending()?"ASC":"DESC",
                        pageable.getSortField(),pageable.isAscending()?"ASC":"DESC"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            PeriodicalMapper.setFindAllPreperadStatement(statement,pageable);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
//                    String str = res.getString("p_name");
                    Long pages = res.getLong("pages");
                    Set<Periodical> periodicals = PeriodicalMapper.getPeriodicalSet(res, pageable.getLimit());
                    for(Periodical p : periodicals){
                        p.setUsers(UserMapper.getPeriodicalUserSet(res, pageable.getLimit(), p.getId()));
                    }
                    return new Page<Periodical>(periodicals,
                            (pageable.getOffset()/pageable.getLimit())+1,
                            Math.ceil((double) pages / pageable.getLimit()));
                }
                LOGGER.info("ResultSet is empty");
                return new Page<Periodical>();
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
                    Set<Periodical> periodicals = PeriodicalMapper.getPeriodicalSet(res, 1);
                    res.last();
                    for(Periodical p : periodicals){
                        p.setUsers(UserMapper.getPeriodicalUserSet(res ,p.getId()));
                    }
                    return new Page<Periodical>(periodicals,
                            0, 0D);
                }
                LOGGER.info("ResultSet is empty");
                return new Page<Periodical>();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Page<Periodical> findBySubject(Subject subj, Pageable pageable)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(
                String.format(statements.getProperty("periodical.filter.by.subject"),
                        pageable.getSortField(),pageable.isAscending()?"ASC":"DESC",
                        pageable.getSortField(),pageable.isAscending()?"ASC":"DESC"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setString(1, subj.name());
            statement.setString(2, subj.name());
            statement.setInt(3, pageable.getLimit());
            statement.setInt(4, pageable.getOffset());
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    Long pages = res.getLong("pages");
                    Set<Periodical> periodicals = PeriodicalMapper.getPeriodicalSet(res, pageable.getLimit());
                    for(Periodical p : periodicals){
                        p.setUsers(UserMapper.getPeriodicalUserSet(res, pageable.getLimit(), p.getId()));
                    }
                    return new Page<Periodical>(periodicals, (pageable.getOffset()/pageable.getLimit())+1,
                            Math.ceil((double) pages / pageable.getLimit()));
                }
                LOGGER.info("ResultSet is empty");
                return new Page<Periodical>(new HashSet<Periodical>(),
                        (pageable.getOffset()/pageable.getLimit())+1, 0D);
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
                    LOGGER.info("Periodical was deleted successfully, p_id[{}]",res.getLong("p_id"));
                    return;
                }
                LOGGER.info("Periodical are not deleted");
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    public void changeUserPeriodicalSubscription(User user, Periodical periodical)throws SQLException{
        connection.setAutoCommit(false);
        boolean isSubscribe = user.getPeriodicals().stream().anyMatch(p->p.getId()==periodical.getId());
        try (PreparedStatement statementUsers = connection.prepareStatement(
                        statements.getProperty(isSubscribe ?"user.update.subscriptions":"user.update.balance"));
             PreparedStatement statementPeriodicals = connection.prepareStatement(
                     statements.getProperty("periodical.update.subscribers"));
             PreparedStatement user_periodicals = connection.prepareStatement(
                     statements.getProperty(isSubscribe?
                             "users_periodicals.insert":"users_periodicals.delete.u_id.and.p_id"))){

            if(isSubscribe){
                statementUsers.setLong(1, user.getSubscriptions());
                statementUsers.setLong(2, user.getBalance());
                statementUsers.setLong(3, user.getId());
                statementUsers.executeUpdate();
            }else if(!isSubscribe){
                statementUsers.setLong(1, user.getBalance());
                statementUsers.setLong(2, user.getId());
                statementUsers.executeUpdate();
            }

            statementPeriodicals.setLong(1, periodical.getSubscribers());
            statementPeriodicals.setLong(2, periodical.getId());
            statementPeriodicals.executeUpdate();

            user_periodicals.setLong(1, user.getId());
            user_periodicals.setLong(2, periodical.getId());
            user_periodicals.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("[{}]: {}", ex.getClass().getSimpleName(), ex.getMessage());
            connection.rollback();
            connection.setAutoCommit(true);
            throw ex;
        }
        connection.commit();
        connection.setAutoCommit(true);
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
