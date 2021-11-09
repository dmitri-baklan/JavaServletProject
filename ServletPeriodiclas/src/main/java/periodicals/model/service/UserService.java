package periodicals.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.dto.UserDTO;
import periodicals.exception.*;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());

    FactoryDAO factoryDAO;

    public UserService() {
        this(FactoryDAO.getInstance());
    }

    public UserService(FactoryDAO factoryDAO) {
        this.factoryDAO = factoryDAO;
    }

    public User getUserAuthority(String email, String password)
            throws UserNotFoundException, IncorrectPasswordException, DataBaseException{
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            if(user.getPassword().equals(DigestUtils.md5Hex(password))){
                return user;
            }
            throw new IncorrectPasswordException();
        }catch (UserNotFoundException | IncorrectPasswordException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }


    public void changeIsActive(Long id)throws UserNotFoundException{
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            User user = userRepository.findReaderById(id)
                    .orElseThrow(UserNotFoundException::new);

            user.setActive(!user.isActive());
            userRepository.save(user);
        }catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }

    public User getUserByEmail(String email) throws UserNotFoundException{
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            return userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);
        }catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public Page<User> getAllReaders(int page, int size, String searchQuery)throws ReaderNotFoundException, DataBaseException{
        Pageable pageable = new Pageable("Name", true, page, size);
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            if(searchQuery.isBlank()){
                return userRepository.findByRole(Role.READER, pageable);
            }
            Page<User> user = userRepository.findByEmail(searchQuery, pageable);
            if(user.getItems().stream().anyMatch(u->u.getRole().equals(Role.ADMINISTRATOR))){
                throw new ReaderNotFoundException();
            }
            return user;
        }catch (ReaderNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public User signUpUser(UserDTO userDTO) throws EmailAlreadyExistException, DataBaseException {
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
                throw new EmailAlreadyExistException();
            }
            String encPassword = DigestUtils.md5Hex(userDTO.getPassword());
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .password(encPassword)
                    .name(userDTO.getName())
                    .surname(userDTO.getSurname())
                    .role(Role.valueOf(userDTO.getRole()))
                    .isActive(true)
                    .balance(Role.valueOf(userDTO.getRole()).equals(Role.READER) ? 0L:null)
                    .subscriptions(Role.valueOf(userDTO.getRole()).equals(Role.READER) ? 0L:null)
                    .build();

            return userRepository.save(user);

        }catch (EmailAlreadyExistException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public User updateUser(UserDTO userDTO) throws UserNotFoundException {
        try(UserDAO userRepository = factoryDAO.createUserDAO()){
            User userToUpdate = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(UserNotFoundException::new);

            String encPassword = DigestUtils.md5Hex(userDTO.getPassword());
            User user = User.builder()
                    .id(userToUpdate.getId())
                    .name(userDTO.getName())
                    .surname(userDTO.getSurname())
                    .email(userDTO.getEmail())
                    .password(encPassword)
                    .periodicals(userToUpdate.getPeriodicals())
                    .role(userToUpdate.getRole())
                    .isActive(userToUpdate.isActive())
                    .balance(userToUpdate.getBalance())
                    .subscriptions(userToUpdate.getSubscriptions())
                    .build();
            userRepository.save(user);
            return user;
        }catch (UserNotFoundException ex){
            throw ex;
        }
        catch (Exception ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }

}
