package periodicals.model.dao;

import periodicals.dto.Page;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.Replenishment;

import java.sql.SQLException;

public interface ReplenishmentDAO extends GenericDAO<Replenishment>{
    Page<Replenishment> findByUserId(Long id, Pageable pageable) throws SQLException;
}
