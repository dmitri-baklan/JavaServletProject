package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileReplenishmentCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileReplenishmentCommand.class.getName());
//    private final UserService userService;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
