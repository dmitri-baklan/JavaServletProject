package periodicals.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.dto.PeriodicalDTO;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.exception.NotEnoughBalanceException;
import periodicals.exception.PeriodicalNotFoundException;
import periodicals.exception.UserNotFoundException;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;

import java.sql.SQLException;
import java.util.Optional;

public class PeriodicalService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicalService.class.getName());

    private PeriodicalDAO periodicalRepository;
    private UserDAO userRepository;

    public PeriodicalService() {
        this(FactoryDAO.getInstance());
    }

    public PeriodicalService(FactoryDAO daoFactory) {
        this.periodicalRepository = daoFactory.createPeriodicalDAO();
        this.userRepository = daoFactory.createUserDAO();
    }

    public void savePeriodical(PeriodicalDTO periodicalDTO){

        Periodical periodical = Periodical
                .builder()
                .name(periodicalDTO.getName())
                .subject(periodicalDTO.getSubject())
                .price(periodicalDTO.getPrice())
                .subscribers(periodicalDTO.getSubscribers())
                .build();
        try{
            periodicalRepository.save(periodical);
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


    public void updatePeriodical(PeriodicalDTO periodicalDTO, Long id){
        try{
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


    /**
     * Change current subscription status of Periodical for current user.<br>
     * If current user already has this periodical in <code>user.periodicals</code> than <code>remove</code><br>
     * If current user is not subscribed yet in this periodical:
     * <ul>
     *     <li>If current <em>periodical price</em> bigger than <em>user balance</em> throw <code>NotEnoughBalanceException</code></li>
     *     <li>If current <em>user balance</em> bigger <em>periodical price</em> than <code>add</code> periodical to current user</li>
     * </ul>
     * @param id of Periodical which should change subscription
     * @param userDetails data for getting current <code>user</code> from DB
     * @throws PeriodicalNotFoundException
     * @throws UserNotFoundException
     * @throws NotEnoughBalanceException
     */
    //TODO CHANGE SUBSCRIPTION!!!!
    public void changeSubscription(Long id, UserDTO userDetails)
            throws PeriodicalNotFoundException, UserNotFoundException, NotEnoughBalanceException{

        try{
            Periodical periodical = periodicalRepository.findById(id)
                    .orElseThrow(PeriodicalNotFoundException::new);

            User user = userRepository.findByEmail(userDetails.getEmail())
                    .orElseThrow(UserNotFoundException::new);

            if(!user.getPeriodicals().remove(periodical)){
                if (user.getBalance() - periodical.getPrice() >= 0) {
                    user.setBalance(user.getBalance() - periodical.getPrice());
                    user.getPeriodicals().add(periodical);
                    periodical.setSubscribers(periodical.getSubscribers()+1);
                    userRepository.save(user);
                    return;
                }
                throw new NotEnoughBalanceException();
            }
            periodical.setSubscribers((periodical.getSubscribers() == 0) ? 0 : (periodical.getSubscribers()-1));
            userRepository.save(user);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }
}
