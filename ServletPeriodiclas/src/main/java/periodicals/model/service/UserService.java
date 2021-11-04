package periodicals.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.dto.Page;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.exception.EmailAlreadyExistException;
import periodicals.exception.UserNotFoundException;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.SQLException;
import java.util.Optional;

public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());

    private UserDAO userRepository;

    public UserService() {
        this(FactoryDAO.getInstance());
    }

    public UserService(FactoryDAO daoFactory) {
        this.userRepository = daoFactory.createUserDAO();
    }

//    @Override
//    public UserDetails loadUserByUsername(String email)throws UserNotFoundException {
//        return new UserDetailsImpl(userRepository.findByEmail(email).
//                orElseThrow(()-> new UsernameNotFoundException(String.format("email %s not found", email))));
//    }

    public User getUserAuthority(String email, String password){
        // TODO: add DigestUtils.md5Hex
        try {
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            if(user.getPassword().equals(DigestUtils.md5Hex(password))){
                return user;
            }
            return new User();
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }


    public void changeIsActive(Long id)throws UserNotFoundException{
        try{
            User user = userRepository.findRedaerById(id)
                    .orElseThrow(UserNotFoundException::new);

            user.setActive(!user.isActive());
            userRepository.save(user);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }

    public User getUserByEmail(String email) throws UserNotFoundException{
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public Page<User> getAllReaders(int page, int size, String searchQuery){
        Pageable pageable = new Pageable("Name", true, page, size);
        try{
            return searchQuery.isBlank() ? userRepository.findByRole(Role.READER, pageable)
                    : userRepository.findByEmail(searchQuery, pageable);
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }
    }

    public void signUpUser(UserDTO userDTO) throws EmailAlreadyExistException {

        try{
            if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
                throw new EmailAlreadyExistException();
            }
            //TODO .upperCase?
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

            userRepository.save(user);

        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }


    }

    public User updateUser(UserDTO userDTO) throws EmailAlreadyExistException {
        try{
//            if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
//                throw new EmailAlreadyExistException();
//            }
            User userToUpdate = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(UserNotFoundException::new);
            ;


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
//            LOGGER.info("User to saving is active: {}", user.isActive());
            userRepository.save(user);
            return user;
        }catch (SQLException ex){
            LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
            throw new DataBaseException();
        }

    }

//    private Pageable buildPage(String sortField, boolean asc, int page, int size) {
//        Optional<Sort> sort = asc ? Optional.of(Sort.by(sortField).ascending())
//                : Optional.of(Sort.by(sortField).descending());
//
//        return PageRequest.of(page - 1, size, sort.get());
//    }
}
