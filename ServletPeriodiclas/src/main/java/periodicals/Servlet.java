package periodicals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.*;

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
