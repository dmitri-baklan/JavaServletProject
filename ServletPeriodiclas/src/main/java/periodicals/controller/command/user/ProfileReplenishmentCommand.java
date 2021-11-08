package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.dto.PeriodicalDTO;
import periodicals.dto.ReplenishmentDTO;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.service.PeriodicalService;
import periodicals.model.service.ReplenishmentService;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ProfileReplenishmentCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileReplenishmentCommand.class.getName());
    private ReplenishmentService replenishmentService;

    public ProfileReplenishmentCommand() {
        this(ReplenishmentService.getInstance());
    }
    public ProfileReplenishmentCommand(ReplenishmentService periodicalService) {
        this.replenishmentService = periodicalService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        if(request.getMethod().equals("GET")){
            return "/user/replenishment.jsp";
        }
        String value = request.getParameter("value");
        String email = (String) request.getSession().getAttribute("email");
        ReplenishmentDTO replenishmentDTO = ReplenishmentDTO.builder().build();
        if(Objects.isNull(value) || value.isBlank()){
            LOGGER.error("Value[{}] are not valid", value);
            request.setAttribute(AttributeKey.ERROR_BLANK, "valid.replenishment.sum.blank");
            return "/periodical/periodicalAdd.jsp";
        }
        replenishmentDTO.setValue(Long.valueOf(value));

        LOGGER.info("ReplenishmentDTO are valid:[{}]", replenishmentDTO);
        try{
            replenishmentService.replenishBalance(email,replenishmentDTO);
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_EXIST, "valid.periodical.name.exists");
            request.setAttribute("periodical", replenishmentDTO);
            return "/user/replenishment.jsp";
        }
        return "redirect:/profile";
    }
}
