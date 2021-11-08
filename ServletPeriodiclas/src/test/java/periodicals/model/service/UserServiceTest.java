package periodicals.model.service;

import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import periodicals.dto.UserDTO;
import periodicals.exception.DataBaseException;
import periodicals.exception.EmailAlreadyExistException;
import periodicals.exception.UserNotFoundException;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest{

    @Mock
    UserDAO userDAO;
    @Mock
    User user;
    UserService userService;
//    private Object User;


    public UserServiceTest(){
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userDAO);
    }

    @Test(expected = EmailAlreadyExistException.class)
    public void testSignUpUser_ShouldThrowEmailAlreadyExist() {
        try{
            given(userDAO.findByEmail("ExistingEmail")).willReturn(
                     Optional.of(User.builder().name("ExistingEmail").build()));

        }catch (SQLException e){
            fail();
        }
        User user = userService.signUpUser(UserDTO.builder().email("ExistingEmail").build());
    }
    @Test(expected = DataBaseException.class)
    public void testSignUpUser_ShouldThrowSQLException() {
        try{
            given(userDAO.findByEmail(anyString())).willReturn(Optional.empty());
            when(userDAO.save(any(User.class))).thenThrow(SQLException.class);

        }catch (SQLException e){
            fail();
        }
        User user = userService.signUpUser(UserDTO.builder().email("TestEmail")
                .name("TestName")
                .surname("TestSurname")
                .password("TestPasword")
                .role("ADMINISTRATOR")
                .build());
    }
    @Test()
    public void testSignUpUser_PasswordArgument() {
        try{
            given(userDAO.findByEmail(anyString())).willReturn(Optional.empty());
            when(userDAO.save(any())).thenReturn(new User());

        }catch (SQLException e){
            fail();
        }
        UserDTO userDTO = UserDTO.builder().email("TestEmail")
                .name("TestName")
                .surname("TestSurname")
                .password("TestPasword")
                .role("ADMINISTRATOR")
                .build();
        String encPassword = DigestUtils.md5Hex(userDTO.getPassword());
        userService.signUpUser(userDTO);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        try{

            verify(userDAO).save(captor.capture());
        }catch (SQLException e){
            fail();
        }
        assertTrue(captor.getValue().getPassword().equals(encPassword));
    }



    @Test()
    public void testChangeIsActive() {
        try{
            given(userDAO.findReaderById(anyLong())).willReturn(
                    Optional.of(User.builder().email("TestEmail").isActive(true).build()));
            when(userDAO.save(any())).thenReturn(new User());
        }catch (SQLException e){
            fail();
        }
        userService.changeIsActive(anyLong());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        try{
            verify(userDAO).save(captor.capture());
        }catch (SQLException e){
            fail();
        }
        assertFalse(captor.getValue().isActive());
    }

    public void testGetUserByEmail() {
    }

    public void testGetAllReaders() {
    }

    public void testGetUserAuthority() {
    }

    public void testUpdateUser() {
    }
}