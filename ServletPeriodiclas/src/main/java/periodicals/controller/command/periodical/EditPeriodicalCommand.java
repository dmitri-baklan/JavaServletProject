package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.dto.PeriodicalDTO;
import periodicals.model.entity.periodical.Subject;
import periodicals.model.service.PeriodicalService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class EditPeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(EditPeriodicalCommand.class.getName());

    private final PeriodicalService periodicalService;

    public EditPeriodicalCommand() {
        this(new PeriodicalService());
    }
    public EditPeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {


        Long periodical_id = Validator.getIdFromRequset(request);
        if(request.getMethod().equals("GET")){
            try {
                request.setAttribute("periodical", periodicalService.getPeriodicalById(periodical_id));
            } catch(Exception ex) {
                LOGGER.error("[{}]{}", ex.getClass().getSimpleName(), ex.getMessage());
                return "redirect:/periodicals";
            }
            return "/periodical/periodicalEdit.jsp";
        }
        String price = request.getParameter("price");
        PeriodicalDTO periodicalDTO = PeriodicalDTO.builder()
                .id(periodical_id)
                .name(request.getParameter("name"))
                .subject(Subject.valueOf(request.getParameter("subject")))
                .build();
        if(!Validator.checkNameRegex(periodicalDTO.getName())){
            LOGGER.error("Name[{}] are not valid", periodicalDTO.getName());
            request.setAttribute(AttributeKey.ERROR_PATTERN_NAME, "valid.periodical.name.regex");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalEdit.jsp";
        }
        if(Objects.isNull(price) || price.isBlank()){
            LOGGER.error("Price[{}] are not valid", periodicalDTO.getPrice());
            request.setAttribute(AttributeKey.ERROR_BLANK, "valid.periodical.price");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalEdit.jsp";
        }
        periodicalDTO.setPrice(Long.valueOf(price));

        LOGGER.info("PeriodicalDTO are valid:[{}]", periodicalDTO);
        try{
            periodicalService.updatePeriodical(periodicalDTO, periodical_id);
        }catch (Exception ex){
            LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
            request.setAttribute(AttributeKey.ERROR_EXIST, "valid.periodical.name.exists");
            request.setAttribute("periodical", periodicalDTO);
            return "/periodical/periodicalEdit.jsp";
        }

        return "redirect:/periodicals";
    }
}
