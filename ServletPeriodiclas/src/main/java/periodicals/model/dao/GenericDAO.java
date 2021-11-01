package periodicals.model.dao;

import java.sql.SQLException;

public interface GenericDAO<T> extends AutoCloseable {
    public T save(T entity) throws SQLException;
}
