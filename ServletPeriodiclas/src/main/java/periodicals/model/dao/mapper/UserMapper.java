package periodicals.model.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.model.dao.impl.JDBCUserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Stream;

public class UserMapper{
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

    public static User extractFromResultSet(ResultSet result) throws SQLException {
        User user = User.builder()
                .id(result.getLong("u_id"))
                .email(result.getString("u_email"))
                .password(result.getString("u_password"))
                .name(result.getString("u_name"))
                .surname(result.getString("u_surname"))
                .role(Role.valueOf(result.getString("u_role")))
                .balance(result.getLong("u_balance"))
                .isActive(result.getBoolean("u_is_active"))
                .subscriptions(result.getLong("u_subscriptions"))
                .build();
        return user;
    }

    public static Set<User> getUserSet(ResultSet result, Integer limit) throws SQLException {
        Set<User> users = new LinkedHashSet<>();
        result.beforeFirst();
        while((result.next()) && (users.size() < limit)){
            if(result.getLong("u_id") > 0)
            {
                users.add(extractFromResultSet(result));
            }
        }
        return users;
    }
    public static void setFindByRolePrepearedStatement(Role role, Integer limit, Integer offset, PreparedStatement statement) throws SQLException {
        statement.setString(1, role.name());
        statement.setString(2, role.name());
        statement.setInt(3, limit);
        statement.setInt(4, offset);
    }

    public static void setUserPreparedStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getRole().name());
        statement.setBoolean(6, user.isActive());
        if(Objects.isNull(user.getBalance()) && Objects.isNull(user.getSubscriptions())){
            statement.setNull(7, Types.BIGINT);
            statement.setNull(8, Types.BIGINT);
            return;
        }
        statement.setLong(7, user.getBalance());
        statement.setLong(8, user.getSubscriptions());
        if(Objects.nonNull(user.getId())){
            statement.setLong(9, user.getId());
        }
    }

    public static Set<User> getPeriodicalUserSet(ResultSet result, Long p_id) throws SQLException {
        Set<User> users = new LinkedHashSet<>();
        result.beforeFirst();
        while((result.next())){
            if(result.getLong("u_id") > 0 && result.getLong("p_id") == p_id)
            {
                users.add(extractFromResultSet(result));
            }
        }
        return users;
    }

    public static Set<User> getPeriodicalUserSet(ResultSet result, Integer limit, Long p_id) throws SQLException {
        Set<User> users = new LinkedHashSet<>();
        result.beforeFirst();
        while((result.next()) && (users.size() < limit)){
            if(result.getLong("u_id") > 0 && result.getLong("p_id") == p_id)
            {
                users.add(extractFromResultSet(result));
            }
        }
        return users;
    }
}
