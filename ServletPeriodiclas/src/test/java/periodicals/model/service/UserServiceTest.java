package periodicals.model.service;

import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import periodicals.dto.Page;
import periodicals.dto.UserDTO;
import periodicals.exception.*;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.pageable.Pageable;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest{

    @Mock
    UserDAO userDAO;
    UserDTO userDTO;
    UserService userService;
    String existingEmail;
    String testPassword;

    public UserServiceTest(){
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userDAO);
    }

    @Before
    public void beforeTest() {
        existingEmail = "ExistingEmail";
        testPassword = "1246";
        userDTO = UserDTO.builder()
                .email("TestEmail")
                .name("TestName")
                .surname("TestSurname")
                .password("TestPasword")
                .role("ADMINISTRATOR")
                .build();

    }

    @Test(expected = EmailAlreadyExistException.class)
    public void testSignUpUser_ShouldThrowEmailAlreadyExist() {
        try{
            given(userDAO.findByEmail(existingEmail)).willReturn(
                     Optional.of(User.builder().name(existingEmail).build()));

        }catch (SQLException e){
            fail();
        }
        User user = userService.signUpUser(UserDTO.builder().email(existingEmail).build());
    }
    @Test(expected = DataBaseException.class)
    public void testSignUpUser_ShouldThrowSQLException() {
        try{
            given(userDAO.findByEmail(anyString())).willReturn(Optional.empty());
            when(userDAO.save(any(User.class))).thenThrow(SQLException.class);
        }catch (SQLException e){
            fail();
        }
        User user = userService.signUpUser(userDTO);
    }
    @Test()
    public void testSignUpUser_PasswordArgument() {
        try{
            given(userDAO.findByEmail(anyString())).willReturn(Optional.empty());
            when(userDAO.save(any())).thenReturn(new User());

        }catch (SQLException e){
            fail();
        }
        String encPassword = DigestUtils.md5Hex(userDTO.getPassword());
        userService.signUpUser(userDTO);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        try{

            verify(userDAO).save(captor.capture());
        }catch (SQLException e){
            fail();
        }
        assertEquals(captor.getValue().getPassword(), encPassword);
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

    @Test(expected = EmailAlreadyExistException.class)
    public void testGetUserByEmail_ShouldThrowEmailAlreadyExist() {
        try{
            given(userDAO.findByEmail(existingEmail)).willReturn(
                    Optional.of(User.builder().name(existingEmail).build()));

        }catch (SQLException e){
            fail();
        }
        User user = userService.signUpUser(UserDTO.builder().email(existingEmail).build());
    }
    @Test
    public void testGetUserByEmail_ShouldReturnUser() {
        
        try{
            given(userDAO.findByEmail(existingEmail)).willReturn(
                    Optional.of(User.builder().email(existingEmail).build()));

        }catch (SQLException e){
            fail();
        }
        User user = userService.getUserByEmail(existingEmail);
        assertEquals(user.getEmail(), existingEmail);
    }

    @Test(expected = ReaderNotFoundException.class)
    public void testGetAllReaders_ShouldThrowReaderNotFound() {
        Set<User> users = new LinkedHashSet<User>();
        User user = User.builder().role(Role.ADMINISTRATOR).build();
        users.add(user);
        Page<User> page = new Page<User>(users, 1, 1D);
        try{
            when(userDAO.findByEmail(eq(existingEmail), any())).thenReturn(page);

        }catch (SQLException e){
            fail();
        }
        userService.getAllReaders(1, 1, existingEmail);
    }

    @Test
    public void testGetAllReaders_ShouldReturnReader() {
        Set<User> users = new LinkedHashSet<User>();
        User user = User.builder().email(existingEmail).role(Role.READER).build();
        users.add(user);
        Page<User> page = new Page<User>(users, 1, 1D);
        try{
            when(userDAO.findByEmail(eq(existingEmail), any())).thenReturn(page);

        }catch (SQLException e){
            fail();
        }
        Page<User> returnedPage = userService.getAllReaders(1, 1, existingEmail);
        assertEquals(1, returnedPage.getItems().size());
        assertEquals(returnedPage.getItems().stream().findFirst().get().getEmail(), existingEmail);
    }

    @Test(expected = IncorrectPasswordException.class)
    public void testGetUserAuthority_ShouldReturnIncorrectPassword() {
        User user = User.builder().email(existingEmail).password(testPassword).build();
        try{
            when(userDAO.findByEmail((existingEmail))).thenReturn(Optional.of(user));

        }catch (SQLException e){
            fail();
        }
        userService.getUserAuthority(existingEmail, testPassword);
    }

    @Test
    public void testGetUserAuthority_ShouldReturnUser() {
        String encPassword = DigestUtils.md5Hex(testPassword);
        User user = User.builder().email(existingEmail).password(encPassword).build();
        try{
            when(userDAO.findByEmail((existingEmail))).thenReturn(Optional.of(user));
        }catch (SQLException e){
            fail();
        }
        User returnedUser = userService.getUserAuthority(existingEmail, testPassword);
        assertNotNull(returnedUser);
        assertEquals(returnedUser.getEmail(), existingEmail);
    }

    @Test
    public void testUpdateUser_ShouldSaveNewUser() {
        User user = User.builder()
                .id(1L)
                .name("OldName")
                .surname("OldSurname")
                .email("TestEmail")
                .password("1246")
                .periodicals(new LinkedHashSet<>())
                .role(Role.ADMINISTRATOR)
                .isActive(true)
                .balance(0L)
                .subscriptions(0L)
                .build();
        try{
            when(userDAO.findByEmail("TestEmail")).thenReturn(Optional.of(user));
            when(userDAO.save(any(User.class))).thenReturn(new User());

        }catch (SQLException e){
            fail();
        }
        userService.updateUser(userDTO);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        try{
            verify(userDAO).save(captor.capture());
        }catch (SQLException e){
            fail();
        }
        assertEquals(captor.getValue().getName(), "TestName");
        assertEquals(captor.getValue().getSurname(), "TestSurname");

    }
}