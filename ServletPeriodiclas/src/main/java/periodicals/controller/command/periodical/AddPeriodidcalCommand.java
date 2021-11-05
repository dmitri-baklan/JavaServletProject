package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;
import periodicals.controller.command.user.LogoutCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPeriodidcalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddPeriodidcalCommand.class.getName());


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
