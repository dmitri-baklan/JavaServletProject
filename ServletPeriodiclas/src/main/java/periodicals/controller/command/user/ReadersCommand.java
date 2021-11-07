package periodicals.controller.command.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.validator.Validator;
import periodicals.dto.Page;
import periodicals.model.entity.periodical.Periodical;
import periodicals.model.entity.user.User;
import periodicals.model.service.UserService;
import periodicals.util.AttributeKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ReadersCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadersCommand.class.getName());

    private final UserService userService;

    public ReadersCommand() {
        this(UserService.getInstance());
    }

    private ReadersCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String searchQuery = Validator.getSearchQueryParam(request, "searchQuery", "");
        Integer page = Validator.getDigitParam(request, "page", "1");
        Integer size = Validator.getDigitParam(request, "size", "5");

        LOGGER.info("[PAGINATION]: page:[{}], size:[{}], searchQuery:[{}]",
                page, size, searchQuery);
        Page<User> readers;
        try{

            readers = userService.getAllReaders(page, size, searchQuery);
        }catch (Exception ex){
                LOGGER.error("[{}]:{}", ex.getClass().getSimpleName(), ex.getMessage());
                request.setAttribute(AttributeKey.ERROR_NOT_FOUND, "readers.not.found");
                return "/user/userList.jsp";

        }

        Map<String, String> request_params = new HashMap<String, String>();
        request_params.put("page", page.toString());
        request_params.put("size", size.toString());
        request_params.put("searchQuery", searchQuery);

        request.setAttribute("request_params", request_params);
        request.setAttribute("readers", readers);

        return "/user/userList.jsp";
    }
}
