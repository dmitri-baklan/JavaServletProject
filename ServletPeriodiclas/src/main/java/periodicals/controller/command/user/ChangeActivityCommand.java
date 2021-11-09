package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.model.service.PeriodicalService;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeActivityCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeActivityCommand.class.getName());

    private final UserService userService;

    public ChangeActivityCommand() {
        this(new UserService());
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

            return "/error/500.jsp";
        }


        return "redirect:/profile/readers";
    }
}
