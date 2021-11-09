package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.user.LogoutCommand;
import periodicals.controller.validator.Validator;
import periodicals.dto.PeriodicalDTO;
import periodicals.dto.UserDTO;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.service.PeriodicalService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class AddPeriodidcalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPeriodidcalCommand.class.getName());

    private final PeriodicalService periodicalService;

    public AddPeriodidcalCommand() {
        this(new PeriodicalService());
    }
    public AddPeriodidcalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if(request.getMethod().equals("GET")){
            return "/periodical/periodicalAdd.jsp";
        }
        String price = request.getParameter("price");
        PeriodicalDTO periodicalDTO = PeriodicalDTO.builder()
                .name(request.getParameter("name"))
                .subject(Subject.valueOf(request.getParameter("subject")))
                .build();
        if(!Validator.checkNameRegex(periodicalDTO.getName())){
            LOGGER.error("Name[{}] are not valid", periodicalDTO.getName());
            request.setAttribute(AttributeKey.ERROR_PATTERN_NAME, "valid.periodical.name.regex");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalAdd.jsp";
        }
        if(Objects.isNull(price) || price.isBlank()){
            LOGGER.error("Price[{}] are not valid", periodicalDTO.getPrice());
            request.setAttribute(AttributeKey.ERROR_BLANK, "valid.periodical.price");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalAdd.jsp";
        }
        periodicalDTO.setPrice(Long.valueOf(price));
        LOGGER.info("PeriodicalDTO are valid:[{}]", periodicalDTO);
        try{
            periodicalService.savePeriodical(periodicalDTO);
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_EXIST, "valid.periodical.name.exists");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalAdd.jsp";
        }

        return "redirect:/periodicals";
    }
}
