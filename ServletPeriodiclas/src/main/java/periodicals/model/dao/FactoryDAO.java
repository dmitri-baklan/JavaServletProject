package periodicals.model.dao;

import periodicals.model.dao.impl.JDBCFactoryDAO;

import java.util.Objects;

public abstract class FactoryDAO {

    private static FactoryDAO factoryDAO;

    public abstract UserDAO createUserDAO();


    public static synchronized FactoryDAO getInstance(){
        return Objects.isNull(factoryDAO) ? new JDBCFactoryDAO() : factoryDAO;
    }
}
