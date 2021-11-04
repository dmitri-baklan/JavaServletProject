package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.controller.validator.Validator;
import periodicals.exception.DataBaseException;
import periodicals.model.entity.user.authority.Role;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditUserCommand.class.getName());
    private final UserService userService;
    public EditUserCommand() {
        this(new UserService());
    }
    public EditUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if(request.getMethod().equals("GET")){
            return "login.jsp";
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(!Validator.checkIsNotBlank(email, password)){
            LOGGER.error("Email[{}] and password[{}] are not valid", email, password);
            request.setAttribute(AttributeKey.ERROR_BLANK, "form.signin.incorrect.data");
            return "login.jsp";
        }
        LOGGER.info("Email[{}] and password[{}] are valid", email, password);
        // TODO AttributeKey
        if (CommandUtility.checkUserIsLogged(request, email)) {
            LOGGER.error("User with email[{}] has already logged in", email);
            request.setAttribute(AttributeKey.ERROR_BLANK,"valid.user.already.logged");
            return "login.jsp";
        }
        LOGGER.info("Email[{}] are not logged in yet", email);
        // TODO AttributeKey
        try {
            Role role = userService.checkUserAuthority(email, password);
            if(role.equals(Role.GUEST)){
                CommandUtility.setUserRole(request, Role.GUEST, null);
                request.setAttribute(AttributeKey.ERROR_BLANK,"form.signin.incorrect.data");
                return "login.jsp";
            }
            CommandUtility.setUserRole(request, role, email);
            CommandUtility.addUserFromContext(request, email);
        }catch(DataBaseException ex) {
            CommandUtility.setUserRole(request, Role.GUEST, null);
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_BLANK,"form.signin.incorrect.data");
            return "login.jsp";
        }
        return "redirect:/profile";
    }
}
