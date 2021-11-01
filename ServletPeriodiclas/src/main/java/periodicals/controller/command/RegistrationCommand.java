package periodicals.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.controller.filter.AuthFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
