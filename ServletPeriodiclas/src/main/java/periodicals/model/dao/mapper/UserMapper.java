package periodicals.model.dao.mapper;

import periodicals.dto.Page;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class UserMapper{ //implements ObjectMapper<User>{
//    private Long id;
//    private String email;
//    private String password;
//    private String name;
//    private String surname;
//    private Role role;
//    private Long balance;
//    private boolean isActive = true;

    public static User extractFromResultSet(ResultSet result) throws SQLException {
        return User.builder()
                .id(result.getLong("id"))
                .email(result.getString("email"))
                .password(result.getString("password"))
                .name(result.getString("name"))
                .surname(result.getString("surname"))
                .role(Role.valueOf(result.getString("role")))
                .balance(result.getLong("balance"))
                .isActive(result.getBoolean("is_active"))
                .build();
    }

    public static Page<User> getUserPage(ResultSet result, Integer limit, Integer offset) throws SQLException {
        List<User> users = new ArrayList<>();
        while (result.next()){
            users.add(extractFromResultSet(result));
        }
        return  new Page<>(users, (offset/limit)+1);
    }

    public static void setUserPreparedStatement(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getSurname());
        statement.setString(5, user.getRole().name());
        statement.setBoolean(6, user.isActive());
        statement.setLong(7, user.getBalance());
    }
}
