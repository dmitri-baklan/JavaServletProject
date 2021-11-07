package periodicals.model.dao;

import periodicals.dto.Page;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO extends GenericDAO<User>{

    Optional<User> findByEmail(String email) throws SQLException ;

    Optional<User> findReaderById(Long id) throws SQLException;

    Page<User> findByRole(Role role, Pageable pageable) throws SQLException;

    Page<User> findByEmail(String email,Pageable pageable) throws SQLException;

}
