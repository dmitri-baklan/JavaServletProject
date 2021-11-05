package periodicals.controller.command.periodical;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscriptionPeriodicalCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionPeriodicalCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
