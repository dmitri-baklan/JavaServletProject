package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeActivityCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeActivityCommand.class.getName());

    private final UserService userService;

    public ChangeActivityCommand() {
        this(UserService.getInstance());
    }

    private ChangeActivityCommand(UserService userService) {
        this.userService = userService;
    }



    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            userService.changeIsActive(Validator.getIdFromRequset(request));
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
//            request.setAttribute(AttributeKey.ERROR_BALANCE, "exception.periodicals.not.enough.balance");
            return "redirect:/profile/readers";
        }


        return "redirect:/profile/readers";
    }
}
