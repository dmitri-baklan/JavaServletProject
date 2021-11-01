package periodicals.model.dao.mapper;

import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalMapper {

    public static Periodical extractFromResultSet(ResultSet result) throws SQLException {
        return Periodical.builder()
                .id(result.getLong("id"))
                .name(result.getString("name"))
                .subject(Subject.valueOf(result.getString("subject")))
                .subscribers(result.getLong("subscribers"))
                .price(result.getLong("price"))
                .build();
    }
}
