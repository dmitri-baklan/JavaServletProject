package periodicals.model.dao;

import periodicals.dto.Page;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.replenishment.Replenishment;

import java.sql.SQLException;
import java.util.Set;

public interface ReplenishmentDAO extends GenericDAO<Replenishment>{
    Set<Replenishment> findByUserId(Long id) throws SQLException;
}
