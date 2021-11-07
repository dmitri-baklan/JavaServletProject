package periodicals.model.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.model.dao.impl.JDBCUserDAO;
import periodicals.model.entity.replenishment.Replenishment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class ReplenishmentMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

    public static Replenishment extractFromResultSet(ResultSet result) throws SQLException {
        return Replenishment.builder()
                .id(result.getLong("r_id"))
                .sum(result.getLong("r_sum"))
                .time(result.getTimestamp("r_time").toLocalDateTime())
                .build();
    }
    public static void setPeriodicalPreparedStatement(Replenishment replenishment, PreparedStatement statement) throws SQLException {
        statement.setLong(1, replenishment.getSum());
        statement.setLong(2, replenishment.getUser().getId());
        statement.setTimestamp(3, Timestamp.valueOf(replenishment.getTime()));
        if(Objects.nonNull(replenishment.getId())){
            statement.setLong(4, replenishment.getId());
        }
    }

    public static Set<Replenishment> getReplenishmentlSet(ResultSet result, Integer limit) throws SQLException {
        Set<Replenishment> replenishments = new LinkedHashSet<>();
        result.beforeFirst();
        while((result.next()) && (replenishments.size() < limit)){
            if(result.getLong("r_id") > 0){
                replenishments.add(extractFromResultSet(result));
            }
        }
        LOGGER.info("Replenishment set: {}", replenishments);
        return replenishments;
    }
}
