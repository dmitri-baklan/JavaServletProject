package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.model.entity.user.authority.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutCommand.class.getName());


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        CommandUtility.deleteUserFromContext(request);
        CommandUtility.setUserRole(request, Role.GUEST, null);

        LOGGER.info("User [{}] logged out",request.getAttribute("email"));
        return "redirect:/welcome";
    }
}
