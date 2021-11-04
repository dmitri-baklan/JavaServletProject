package periodicals.model.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.model.dao.impl.JDBCUserDAO;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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

    public static Set<Periodical> getPeriodicalSet(ResultSet result, Integer limit) throws SQLException {
        Set<Periodical> periodiclas = new HashSet<>();
        result.beforeFirst();
        while((result.next()) && (periodiclas.size() < limit)){
            if(result.getLong("p_id") > 0){
                periodiclas.add(extractFromResultSet(result));
            }
        }
        LOGGER.info("Users set adding to page: {}", periodiclas);
        return periodiclas;
        //new Page<>(users, (offset/limit)+1);
    }
}
