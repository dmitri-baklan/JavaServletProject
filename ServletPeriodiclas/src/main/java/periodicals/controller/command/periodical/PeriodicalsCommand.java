package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.dto.Page;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.service.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PeriodicalsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodicalsCommand.class.getName());

    private final PeriodicalService periodicalService;

    public PeriodicalsCommand() {
        this(new PeriodicalService());
    }
    public PeriodicalsCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String sortField = Validator.getFieldSortParam(request, "sortFiled", "p_name");
        String subject = Validator.getFieldSortParam(request, "subject", "");
        boolean asc = Validator.isDirectionAsc(request, "asc", true);
        String searchQuery = Validator.getSearchQueryParam(request, "searchQuery", "");
        Integer page = Validator.getDigitParam(request, "page", "1");
        Integer size = Validator.getDigitParam(request, "size", "5");
        String email = (String)request.getSession().getAttribute("email");

        LOGGER.info("[PAGINATION]: sortFiled:[{}], subject:[{}], direction:[{}], page:[{}], size:[{}], searchQuery:[{}]",
                sortField, subject, asc, page, size, searchQuery);

        Page<Periodical> periodicals = periodicalService.getAllPeriodicals(sortField, subject,
                asc, page, size, searchQuery);

        Map<String, Boolean> user_subscribe = new HashMap<String, Boolean>();

        periodicals.getItems()
                .forEach(p->user_subscribe.put(p.getName(),
                        p.getUsers().stream()
                                .anyMatch(u->u.getEmail().equals(email))));
        Map<String, String> request_params = new HashMap<String, String>();
        request_params.put("sortField", sortField);
        request_params.put("subject", subject);
        request_params.put("asc", Boolean.toString(asc));
        request_params.put("page", page.toString());
        request_params.put("size", size.toString());
        request_params.put("searchQuery", searchQuery);

        request.setAttribute("request_params", request_params);
        request.setAttribute("periodicals", periodicals);
        request.setAttribute("user_subscribe", user_subscribe);

//        (String sortField, String subject,
//        boolean asc, int page,
//        int size, String searchQuery

        return "/periodical/periodicals.jsp";
    }
}
