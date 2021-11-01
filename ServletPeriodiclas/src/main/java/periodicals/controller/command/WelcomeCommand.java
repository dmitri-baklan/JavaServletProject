package periodicals.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "home.jsp";
    }
}
