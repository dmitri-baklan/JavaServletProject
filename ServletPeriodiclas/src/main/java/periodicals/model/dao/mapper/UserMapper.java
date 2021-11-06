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

public class UserMapper{ //implements ObjectMapper<User>{
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class.getName());

//    private Long id;
//    private String email;
//    private String password;
//    private String name;
//    private String surname;
//    private Role role;
//    private Long balance;
//    private boolean isActive = true;

    public static User extractFromResultSet(ResultSet result) throws SQLException {
        LOGGER.info("Building user from ResultSet");
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
        LOGGER.info("Builded user: {}", user);
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
        LOGGER.info("Users set: {}", users);
        return users;
                //new Page<>(users, (offset/limit)+1);
    }
    public static void setFindByRolePrepearedStatement(Role role, Integer limit, Integer offset, PreparedStatement statement) throws SQLException {
        statement.setString(1, role.name());
        statement.setInt(2, limit);
        statement.setInt(3, offset);
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

    public static Set<User> getPeriodicalUserSet(ResultSet result, Integer limit, Long p_id) throws SQLException {
        Set<User> users = new HashSet<>();
        result.beforeFirst();
        while((result.next()) && (users.size() < limit)){
            if(result.getLong("u_id") > 0 && result.getLong("p_id") == p_id)
            {
                users.add(extractFromResultSet(result));
            }
        }
        LOGGER.info("Users set: {}", users);
        return users;
        //new Page<>(users, (offset/limit)+1);
    }
}
