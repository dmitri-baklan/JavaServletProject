package periodicals;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.*;
import periodicals.dto.Page;
import periodicals.dto.PeriodicalDTO;
import periodicals.dto.UserDTO;
import periodicals.exception.PeriodicalAlreadyExistException;
import periodicals.exception.PeriodicalNotFoundException;
import periodicals.exception.UserNotFoundException;
import periodicals.exception.UserNotSavedException;
import periodicals.model.dao.FactoryDAO;
import periodicals.model.dao.PeriodicalDAO;
import periodicals.model.dao.UserDAO;
import periodicals.model.dao.impl.JDBCFactoryDAO;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.entity.user.User;
import periodicals.model.service.PeriodicalService;
import periodicals.model.service.UserService;
import periodicals.util.DBProperty;

import javax.sql.DataSource;

import static org.junit.Assert.*;

public class PeriodicalServiceTest {
    private static Periodical insertedPeriodical;
    private static DataSource dataSource;
    private static PeriodicalDAO periodicalDAO;
    private static UserDAO userDAO;
    private static PeriodicalService periodicalService;
    private static FactoryDAO factoryDAO;
    @BeforeClass
    public static void beforeClass(){
        DBProperty prop = DBProperty.getDbProperty();

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(prop.getProperty("db.test.driver"));
        ds.setUrl(String.format("%s://localhost:%s/%s",prop.getProperty("db.test.url"), prop.getProperty("db.test.port"), prop.getProperty("db.test.name")));
        ds.setUsername(prop.getProperty("db.test.username"));
        ds.setPassword(prop.getProperty("db.test.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        dataSource = ds;
        factoryDAO = new JDBCFactoryDAO(dataSource);
        periodicalDAO = factoryDAO.createPeriodicalDAO();
        userDAO = factoryDAO.createUserDAO();
    }

    @Before
    public void beforeTest() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        try {
            insertedPeriodical = periodicalService.savePeriodical(
                    PeriodicalDTO.builder()
                            .name("TestName")
                            .price(100L)
                            .subject(Subject.NEWS)
                            .subscribers(0L)
                            .build());
        } catch (Exception ex) {
            assertTrue(false);
            try {
                periodicalDAO.close();
            } catch (Exception e) {
                assertTrue(false);
            }
        }
    }

    @Test(expected = PeriodicalAlreadyExistException.class)
    public void shouldThrowPeriodicalAlreadyExistException() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        periodicalService.savePeriodical(PeriodicalDTO.builder().name("TestName").build());
    }

    @Test(expected = PeriodicalAlreadyExistException.class)
    public void shouldThrowPeriodicalAlreadyExistExceptionWhenUpdate() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        periodicalService.updatePeriodical(PeriodicalDTO.builder().name("TestName").subject(Subject.SPORT).build(),
                insertedPeriodical.getId()+1);
    }

    @Test(expected = PeriodicalNotFoundException.class)
    public void shouldThrowPeriodicalNotFoundException() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        periodicalService.getPeriodicalById(insertedPeriodical.getId()+1);
    }

    @Test
    public void shouldGetPagePeriodical() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        Page<Periodical> periodical = null;
        try{
             periodical = periodicalService.getAllPeriodicals("p_name",
                    "", true, 1, 5, "");
        }catch (Exception e) {
            assertTrue(false);
        }
        assertNotNull(periodical);
        assertTrue(periodical.getItems().stream().anyMatch(p->p.getName().equals(insertedPeriodical.getName())));
    }

    @Test
    public void shouldGetEmptyPage() {
        PeriodicalService periodicalService = new PeriodicalService(factoryDAO, periodicalDAO, userDAO);
        Page<Periodical> periodical = null;
        try{
            periodical = periodicalService.getAllPeriodicals("p_name",
                    "", true, 1, 5, "NonExistTestName");
        }catch (Exception e) {
            assertTrue(false);
        }
        assertNotNull(periodical);
        assertTrue(periodical.getItems().isEmpty());
    }

    @After
    public void afterTest() {
        try {
            periodicalDAO.deleteById(insertedPeriodical.getId());
        } catch (Exception ex) {
            assertTrue(false);
            try {
                periodicalDAO.close();
            } catch (Exception e) {
                assertTrue(false);
            }
        }

    }

    @AfterClass
    public static void afterClass() {
        try {
            periodicalDAO.close();
        } catch (Exception e) {
            assertTrue(false);
        }
    }

}
