package periodicals.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.dto.PeriodicalDTO;
import periodicals.dto.UserDTO;
import periodicals.exception.*;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class PeriodicalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicalService.class.getName());

    private static PeriodicalService instance;

    public static PeriodicalService getInstance(){
        if(instance == null){
            instance = new PeriodicalService();
        }
        return instance;
    }

    FactoryDAO daoFactory;
    private PeriodicalDAO periodicalRepository;
    private UserDAO userRepository;

    private PeriodicalService() {
        this.daoFactory = FactoryDAO.getInstance();
        periodicalRepository = daoFactory.createPeriodicalDAO();
        userRepository =daoFactory.createUserDAO();
    }

    public PeriodicalService(FactoryDAO factoryDAO, PeriodicalDAO periodicalDAO, UserDAO userDAO) {
        this.daoFactory = factoryDAO;
        periodicalRepository = periodicalDAO;
        userRepository = userDAO;
    }

    public Periodical savePeriodical(PeriodicalDTO periodicalDTO)throws PeriodicalAlreadyExistException {
        try{
            if(!(periodicalRepository.findByName(periodicalDTO.getName()).getItems().isEmpty())){
                throw new PeriodicalAlreadyExistException();
            }
            Periodical periodical = Periodical
                    .builder()
                    .id(periodicalDTO.getId())
                    .name(periodicalDTO.getName())
                    .subject(periodicalDTO.getSubject())
                    .price(periodicalDTO.getPrice())
                    .subscribers(periodicalDTO.getSubscribers())
                    .build();
            return periodicalRepository.save(periodical);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public Periodical getPeriodicalById(Long id)throws PeriodicalNotFoundException {
        try {
            return periodicalRepository.findById(id).orElseThrow(PeriodicalNotFoundException::new);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }


    public Page<Periodical> getAllPeriodicals(String sortField, String subject,
                                              boolean asc, int page,
                                              int size, String searchQuery){

        Pageable pageable = new Pageable(sortField, asc, page, size);
        try{
            if(subject.isBlank()){
                return searchQuery.isBlank() ? periodicalRepository.findAll(pageable)
                        : periodicalRepository.findByName(searchQuery);

            }
            return searchQuery.isBlank() ? periodicalRepository.findBySubject(Subject.valueOf(subject), pageable)
                    : periodicalRepository.findByName(searchQuery);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }


    public void updatePeriodical(PeriodicalDTO periodicalDTO, Long id)throws PeriodicalAlreadyExistException{
        try{
            Page<Periodical> periodical = periodicalRepository.findByName(periodicalDTO.getName());
            LOGGER.info("Periodical:{}", periodical.getItems().stream().findFirst());
            if(!periodical.getItems().isEmpty()){
                if(periodical.getItems().stream().anyMatch(p->p.getId()!=id)){
                    throw new PeriodicalAlreadyExistException();
                }
            }
            periodicalRepository.save(
                    Periodical.builder()
                            .id(id)
                            .name(periodicalDTO.getName())
                            .subject(periodicalDTO.getSubject())
                            .price(periodicalDTO.getPrice())
                            .subscribers(periodicalDTO.getSubscribers())
                            .build()
            );
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public void deletePeriodical(Long id){
        try{
            periodicalRepository.deleteById(id);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }



    public void changeSubscription(Long periodical_id, String user_email)
            throws PeriodicalNotFoundException, UserNotFoundException, NotEnoughBalanceException{

        try{
            Periodical periodical = periodicalRepository.findById(periodical_id)
                    .orElseThrow(PeriodicalNotFoundException::new);
            User user = userRepository.findByEmail(user_email)
                    .orElseThrow(UserNotFoundException::new);

            if(!user.getPeriodicals().removeIf(p-> p.getId() == periodical.getId())){
                if (user.getBalance() - periodical.getPrice() >= 0) {
                    user.setBalance(user.getBalance() - periodical.getPrice());
                    periodical.setSubscribers(periodical.getSubscribers()+1);
                    user.setSubscriptions(user.getSubscriptions()+1);
                    user.getPeriodicals().add(periodical);
                    periodicalRepository.changeUserPeriodicalSubscription(user, periodical);
                    return;
                }
                throw new NotEnoughBalanceException();
            }
            periodical.setSubscribers((periodical.getSubscribers() == 0) ? 0 : (periodical.getSubscribers()-1));
            user.setSubscriptions((user.getSubscriptions() == 0) ? 0 : (user.getSubscriptions()-1));
            periodicalRepository.changeUserPeriodicalSubscription(user, periodical);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }
}
