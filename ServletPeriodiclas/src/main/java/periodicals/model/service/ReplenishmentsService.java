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

public class ReplenishmentsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplenishmentsService.class.getName());

    private ReplenishmentDAO replenishmentRepository;
    private UserDAO userRepository;

    public ReplenishmentsService() {
        this(FactoryDAO.getInstance());
    }

    public ReplenishmentsService(FactoryDAO daoFactory) {
        this.replenishmentRepository = daoFactory.createReplenishmentDAO();
        this.userRepository = daoFactory.createUserDAO();
    }




    public void replenishBalance(UserDTO userDetails, ReplenishmentDTO replenishmentDTO) throws RuntimeException {
        try{
            User user = userRepository.findByEmail(userDetails.getEmail())
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

    public Page<Replenishment> getAllReplenishments(int page, int size,
                                                    String sortField,
                                                    UserDTO userDetails)throws UserNotFoundException{

        try {
            User user = userRepository.findByEmail(userDetails.getEmail())
                    .orElseThrow(UserNotFoundException::new);

            Pageable pageable = new Pageable(sortField, false, page, size);
            return replenishmentRepository.findByUserId(user.getId(), pageable);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

}
