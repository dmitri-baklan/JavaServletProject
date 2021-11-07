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

    private ReplenishmentDAO replenishmentRepository;
    private UserDAO userRepository;

    private static ReplenishmentService instance;

    public static ReplenishmentService getInstance(){
        if(instance == null){
            instance = new ReplenishmentService();
        }
        return instance;
    }

    private ReplenishmentService() {
        this(FactoryDAO.getInstance());
    }

    private ReplenishmentService(FactoryDAO daoFactory) {
        this.replenishmentRepository = daoFactory.createReplenishmentDAO();
        this.userRepository = daoFactory.createUserDAO();
    }

    public void replenishBalance(String email, ReplenishmentDTO replenishmentDTO) throws RuntimeException {
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);

            user.setBalance(user.getBalance() + replenishmentDTO.getValue());
            userRepository.save(user);

            replenishmentRepository.save(Replenishment.builder()
                    .sum(replenishmentDTO.getValue())
                    .user(user)
                    .time(LocalDateTime.now())
                    .build());
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public Set<Replenishment> getAllReplenishments(String email)throws UserNotFoundException{

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);

//            Pageable pageable = new Pageable(sortField, false, page, size);
            return replenishmentRepository.findByUserId(user.getId());
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

}
