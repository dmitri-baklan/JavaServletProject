package periodicals.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.*;
import periodicals.controller.command.guest.LoginCommand;
import periodicals.controller.command.guest.RegistrationCommand;
import periodicals.controller.command.guest.WelcomeCommand;
import periodicals.controller.command.user.EditUserCommand;
import periodicals.controller.command.user.LogoutCommand;
import periodicals.controller.command.user.ProfileCommand;
import periodicals.controller.command.user.ProfileReplenishmentCommand;
import periodicals.util.AttributeKey;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class Servlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Servlet.class.getName());

    Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        commands.put("/welcome", new WelcomeCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/profile", new ProfileCommand());
        commands.put("/registration", new RegistrationCommand());
        commands.put("/logout", new LogoutCommand());
        commands.put("/profile/edit", new EditUserCommand());
        commands.put("/profile/replenishment", new ProfileReplenishmentCommand());
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
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute(AttributeKey.LOGGED_USERS);
        loggedUsers = Objects.isNull(loggedUsers)? new HashSet<String>() : loggedUsers;
        LOGGER.info("logged user:{}", loggedUsers);
//        LOGGER.info("Request id:{}", request.getCookies());
        LOGGER.info("Cookies:");
        Arrays.stream(request.getCookies()).forEach(c->LOGGER.info("{}:[{}]", c.getName(), c.getValue()));
        String path = request.getRequestURI().replaceAll(".*/app", "");
        LOGGER.info("path:{}", path);
        Command command = commands.getOrDefault(path, commands.get("/welcome"));
        LOGGER.info("command: " + command.getClass().toString());
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
