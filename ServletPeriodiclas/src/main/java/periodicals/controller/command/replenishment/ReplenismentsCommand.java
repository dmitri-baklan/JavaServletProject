package periodicals.controller.command.replenishment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.user.ChangeActivityCommand;
import periodicals.dto.Page;
import periodicals.model.entity.replenishment.Replenishment;
import periodicals.model.service.ReplenishmentService;
import periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class ReplenismentsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplenismentsCommand.class.getName());

    private final ReplenishmentService replenishmentService;

    public ReplenismentsCommand() {
        this(ReplenishmentService.getInstance());
    }

    private ReplenismentsCommand(ReplenishmentService replenishmentService) {
        this.replenishmentService = replenishmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Set<Replenishment> replenishments;
        try{
            replenishments = replenishmentService.getAllReplenishments(
                    (String) request.getSession().getAttribute("email"));
            request.setAttribute("replenishments", replenishments);

        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            return "/error/500.jsp";
        }
        return "/replenishment/replenishments.jsp";
    }
}
