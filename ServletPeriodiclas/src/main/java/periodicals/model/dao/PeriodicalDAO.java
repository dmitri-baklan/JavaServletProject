package periodicals.model.dao;

import periodicals.dto.Page;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;

import java.sql.SQLException;
import java.util.Optional;

public interface PeriodicalDAO extends GenericDAO<Periodical>{
    Optional<Periodical> findById(Long id) throws SQLException;

    Page<Periodical> findAll(Pageable pageable) throws SQLException;

    Page<Periodical> findByName(String searchQuery) throws SQLException;

    Page<Periodical> findBySubject(Subject subj, Pageable pageable) throws SQLException;

    void deleteById(Long id)throws SQLException;
}
