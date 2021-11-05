package periodicals.model.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.model.dao.ReplenishmentDAO;
import periodicals.model.dao.mapper.ReplenishmentMapper;
import periodicals.model.dao.mapper.UserMapper;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.replenishment.Replenishment;
import periodicals.model.entity.user.User;
import periodicals.util.DBProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JDBCReplenishmentDAO implements ReplenishmentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCPeriodicalDAO.class.getName());

    private Connection connection;
    private DBProperty statements;

    public JDBCReplenishmentDAO(Connection connection){
        this.connection = connection;
        this.statements = DBProperty.getDbProperty();
    }

    @Override
    public Replenishment save(Replenishment entity) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(
                statements.getProperty("replenishment.insert"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            ReplenishmentMapper.setPeriodicalPreparedStatement(entity, statement);
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    entity.setId(res.getLong("r_id"));
                    LOGGER.info("Replenishment saved successfully, id[{}]", entity.getId());
                    return entity;
                }
                LOGGER.info("Replenishment not saved");
                throw new SQLException();
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }
//    Optional<Replenishment> replenishment = Optional.of(ReplenishmentMapper.extractFromResultSet(res));
//                    res.last();
//                    replenishment.get().setUser(UserMapper
//                                                        .getUserSet(res, 1).stream()
//                            .findFirst()
//                            .orElseThrow(UserNotFoundException::new));
    @Override
    public Page<Replenishment> findByUserId(Long id, Pageable pageable)throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(statements.getProperty("replenishment.find.by.user_id"),
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)){
            statement.setLong(1, id);
            statement.setLong(2, pageable.getLimit());
            statement.setLong(3, pageable.getOffset());
            try(ResultSet res = statement.executeQuery()){
                if(res.first()){
                    User user = UserMapper.getUserSet(res, 1).stream().findAny().get();
                    res.last();
                    //Optional<Replenishment> replenishment = Optional.of(ReplenishmentMapper.extractFromResultSet(res));
                    Set<Replenishment> replenishments = ReplenishmentMapper.getReplenishmentlSet(res, res.getRow());
                    replenishments.forEach(r->r.setUser(user));
                    // TODO fix total pages!
                    return new Page<Replenishment>(replenishments,
                            (pageable.getOffset()/pageable.getLimit())+1, 0D);
                }
                // TODO fix total pages!
                LOGGER.info("ResultSet is empty");
                return new Page<Replenishment>(new HashSet<Replenishment>(), 0, 0D);
            }
        } catch(SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void close() throws Exception {

    }
}
