package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.service.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PagePeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(PagePeriodicalCommand.class.getName());

    private PeriodicalService periodicalService;
//
    public PagePeriodicalCommand() {
        this(PeriodicalService.getInstance());
    }
    public PagePeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
//        Long id = Validator.getIdFromRequset(request);
//
//        Periodical periodical = periodicalService.getPeriodicalById(id);
//        request.setAttribute("periodical",periodical );
//        request.setAttribute("users", periodical.getUsers());

        return "/periodical/periodicalPage.jsp";
    }
}
