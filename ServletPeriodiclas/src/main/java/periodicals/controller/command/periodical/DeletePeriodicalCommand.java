package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.model.service.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletePeriodicalCommand.class.getName());

    private final PeriodicalService periodicalService;

    public DeletePeriodicalCommand() {
        this(new PeriodicalService());
    }
    public DeletePeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            periodicalService.deletePeriodical(Validator.getIdFromRequset(request));
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            return "redirect:/periodicals";
        }

        return "redirect:/periodicals";
    }
}
