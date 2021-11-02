package periodicals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.*;
import periodicals.controller.command.guest.LoginCommand;
import periodicals.controller.command.guest.RegistrationCommand;
import periodicals.controller.command.guest.WelcomeCommand;
import periodicals.controller.command.user.LogoutCommand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Servlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servlet.class.getName());

    Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        commands.put("/welcome", new WelcomeCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/logout", new LogoutCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO:TEST METHOD find user by email CALL!!
//        JDBCFactoryDAO factoryDAO = new JDBCFactoryDAO();
//        UserDAO userDAO = factoryDAO.createUserDAO();
////        Integer offset;
////                    Integer limit;
////                    String orderBy;
////                    String direction;
//        Pageable pageable = Pageable.builder()
//                .offset(0)
//                .limit(4)
//                        .orderBy("name")
//                                .direction("ASCENDING").build();
//        try{
//            userDAO.findByRole(Role.READER,pageable);
//        }
//        catch(Exception ex){
//
//        }
//

        String path = request.getRequestURI();
        LOGGER.info("path:{}", path);
        //path = path.replaceAll(".*/", "");

        Command command = commands.getOrDefault(path, commands.get("/welcome"));
        LOGGER.info("command: " + command.toString());

        String page = command.execute(request,response);

        if(page.contains("redirect:")){
            LOGGER.info("RIDERECTION, page:{}", page);
            response.sendRedirect(page.replace("redirect:", "/app"));
        }else {
            LOGGER.info("FORWARD, page:{}", page);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
