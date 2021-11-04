package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.exception.UserNotFoundException;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCommand.class.getName());

    private final UserService userService;

    public ProfileCommand() {
        this(new UserService());
    }

    private ProfileCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("user", userService.getUserByEmail((String)request.getSession().getAttribute("email")));
        }catch(UserNotFoundException ex) {
            LOGGER.error("[{}]{}", ex.getClass().getSimpleName(), ex.getMessage());
            // TODO: erros hadling
//            request.setAttribute(AttributeKey.ERROR_CUSTOM.value(),
//                    LangProperties.getProperty("errors.custom.user.not.found", CommandUtility.getLocale(request)));
            return "redirect:home.jsp";
        }
        return "user/profile.jsp";
    }
}
