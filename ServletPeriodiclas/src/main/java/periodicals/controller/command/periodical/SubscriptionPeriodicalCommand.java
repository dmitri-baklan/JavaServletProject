package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.model.service.PeriodicalService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscriptionPeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionPeriodicalCommand.class.getName());
    private final PeriodicalService periodicalService;

    public SubscriptionPeriodicalCommand() {
        this(PeriodicalService.getInstance());
    }
    public SubscriptionPeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            periodicalService.changeSubscription(Validator.getIdFromRequset(request),
                    (String)request.getSession().getAttribute("email"));
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_BALANCE, "exception.periodicals.not.enough.balance");
            return "redirect:/periodicals";
        }

        return "redirect:/periodicals";
    }
}
