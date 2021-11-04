package periodicals.model.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.model.dao.impl.JDBCUserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PeriodicalMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

    public static Periodical extractFromResultSet(ResultSet result) throws SQLException {
        return Periodical.builder()
                .id(result.getLong("p_id"))
                .name(result.getString("p_name"))
                .subject(Subject.valueOf(result.getString("p_subject")))
                .subscribers(result.getLong("p_subscribers"))
                .price(result.getLong("p_price"))
                .build();
    }
    public static void setPeriodicalPreparedStatement(Periodical periodical, PreparedStatement statement) throws SQLException {
        statement.setString(1, periodical.getName());
        statement.setString(2, periodical.getSubject().name());
        statement.setLong(3, periodical.getPrice());
        statement.setLong(4, periodical.getSubscribers());
        if(Objects.nonNull(periodical.getId())){
            statement.setLong(5, periodical.getId());
        }
    }

    public static void setFindAllPreperadStatement(PreparedStatement statement,Pageable pageable)throws SQLException{
        statement.setString(1, pageable.getSortField());
        statement.setInt(2, pageable.getLimit());
        statement.setInt(3, pageable.getOffset());
    }

    public static Set<Periodical> getPeriodicalSet(ResultSet result, Integer limit) throws SQLException {
        Set<Periodical> periodicals = new HashSet<>();
        result.beforeFirst();
        while((result.next()) && (periodicals.size() < limit)){
            if(result.getLong("p_id") > 0){
                periodicals.add(extractFromResultSet(result));
            }
        }
        LOGGER.info("Periodicals set: {}", periodicals);
        return periodicals;
    }
}
