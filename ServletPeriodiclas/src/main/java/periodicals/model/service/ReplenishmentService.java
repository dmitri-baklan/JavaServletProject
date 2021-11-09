package periodicals.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.dto.ReplenishmentDTO;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.exception.UserNotFoundException;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.ReplenishmentDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.replenishment.Replenishment;
import periodicals.model.entity.user.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class ReplenishmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplenishmentService.class.getName());

    FactoryDAO factoryDAO;

    public ReplenishmentService() {
        this(FactoryDAO.getInstance());
    }

    private ReplenishmentService(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public void replenishBalance(String email, ReplenishmentDTO replenishmentDTO) throws RuntimeException {
        try(ReplenishmentDAO replenishmentRepository = factoryDAO.createReplenishmentDAO();
            UserDAO userRepository = factoryDAO.createUserDAO();){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);

            user.setBalance(user.getBalance() + replenishmentDTO.getValue());
            userRepository.save(user);

            replenishmentRepository.save(Replenishment.builder()
                    .sum(replenishmentDTO.getValue())
                    .user(user)
                    .time(LocalDateTime.now())
                    .build());
        }catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public Set<Replenishment> getAllReplenishments(String email)throws UserNotFoundException{

        try(ReplenishmentDAO replenishmentRepository = factoryDAO.createReplenishmentDAO();
             UserDAO userRepository = factoryDAO.createUserDAO();){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);
            return replenishmentRepository.findByUserId(user.getId());
        }catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

}
