package periodicals.controller.command.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.CommandUtility;
import periodicals.controller.filter.AuthFilter;
import periodicals.controller.validator.Validator;
import periodicals.exception.DataBaseException;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class.getName());
    private final UserService userService;
    public LoginCommand() {
        this(UserService.getInstance());
    }
    public LoginCommand(UserService userService) {
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
            User user = userService.getUserAuthority(email, password);
//            if(user.getRole().equals(Role.GUEST)){
//                CommandUtility.setUserRole(request, response, user);
//                request.setAttribute(AttributeKey.ERROR_BLANK,"form.signin.incorrect.data");
//                return "login.jsp";
//            }
            CommandUtility.setUserRole(request, response, user);
            CommandUtility.addUserFromContext(request, email);
        }catch(Exception ex) {
            User user = new User();
            user.setRole(Role.GUEST);
            CommandUtility.setUserRole(request, response, user);
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_BLANK,"form.signin.incorrect.data");
            return "login.jsp";
        }
        return "redirect:/profile";
    }

}
