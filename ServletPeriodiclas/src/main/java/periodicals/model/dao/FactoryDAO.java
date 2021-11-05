package periodicals.model.dao;

import periodicals.model.dao.impl.JDBCFactoryDAO;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.replenishment.Replenishment;

import java.util.Objects;

public abstract class FactoryDAO {

    private static FactoryDAO factoryDAO;

    public abstract UserDAO createUserDAO();

    public abstract PeriodicalDAO createPeriodicalDAO();

    public abstract ReplenishmentDAO createReplenishmentDAO();

    public static synchronized FactoryDAO getInstance(){
        return Objects.isNull(factoryDAO) ? new JDBCFactoryDAO() : factoryDAO;
    }
}
