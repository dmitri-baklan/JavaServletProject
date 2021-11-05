package periodicals.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.*;
import periodicals.controller.command.guest.LoginCommand;
import periodicals.controller.command.guest.RegistrationCommand;
import periodicals.controller.command.guest.WelcomeCommand;
import periodicals.controller.command.periodical.*;
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
        commands.put("/periodicals", new PeriodicalsCommand());
        commands.put("/periodicals/\\d+", new PagePeriodicalCommand());
        commands.put("/periodicals/d+/edit", new EditPeriodicalCommand());
        commands.put("/periodicals/add", new AddPeriodidcalCommand());
        commands.put("/periodicals/d+/delete", new DeletePeriodicalCommand());
        commands.put("/periodicals/d+/subscription", new SubscriptionPeriodicalCommand());
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

        String path = request.getRequestURI().replaceAll(".*/app", "");
        LOGGER.info("path:{}", path);
        Command command = getCommandByURI(path);
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

    private  Command getCommandByURI(String uri) {
        Optional <String> regex = commands.keySet().stream().filter(uri::matches).findFirst();
        return commands.get(regex.isPresent() ? regex.get() : "\\/welcome\\/?");
    }
}
