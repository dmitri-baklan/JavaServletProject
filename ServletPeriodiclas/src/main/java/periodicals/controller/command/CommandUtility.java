package periodicals.controller.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.model.entity.user.User;
import periodicals.model.entity.user.authority.Role;
import periodicals.util.AttributeKey;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class CommandUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandUtility.class.getName());

    private CommandUtility() {}

    public static String setUserRole(HttpServletRequest req, HttpServletResponse res, User user) {
        HttpSession session = req.getSession();
//        res.addCookie(new Cookie("user_email", email));
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole().name());
        session.setAttribute("name", user.getName());
        session.setAttribute("surname", user.getSurname());

        LOGGER.info("Adding email[{}] and role[{}] to session ", user.getEmail(), user.getRole().name());
        return user.getEmail();
    }

    public static boolean checkUserIsLogged(HttpServletRequest req, String email) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) req.getSession().getServletContext()
                .getAttribute(AttributeKey.LOGGED_USERS);
        loggedUsers = Objects.isNull(loggedUsers)? new HashSet<String>() : loggedUsers;

        if (loggedUsers.stream().anyMatch(email::equals)) {
            LOGGER.info("User with email [{}] exists in context", email);
            return true;
        }
        LOGGER.info("User with email [{}] doesn`t exist in context", email);
        return false;
    }


    public static void addUserFromContext(HttpServletRequest req, String email) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) req.getSession().getServletContext()
                .getAttribute(AttributeKey.LOGGED_USERS);
        loggedUsers = Objects.isNull(loggedUsers)? new HashSet<String>() : loggedUsers;
        loggedUsers.add(email);
        req.getSession().getServletContext().setAttribute(AttributeKey.LOGGED_USERS, loggedUsers);
        LOGGER.info("[{}] was added to context", email);
    }
//TODO: cookieException?
    public static void deleteUserFromContext(HttpServletRequest req, HttpServletResponse res) {
        ServletContext context = req.getServletContext();
        String email = (String) req.getSession().getAttribute("email");
//        Cookie cookie = Arrays.stream(req.getCookies())
//                .filter(a->a.getName().equals("user_email"))
//                .findFirst()
//                .orElse(null);
//        cookie.setMaxAge(0);
//        res.addCookie(cookie);
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute("loggedUsers");
        loggedUsers = Objects.isNull(loggedUsers)? new HashSet<String>() : loggedUsers;
        loggedUsers.remove(email);

        context.setAttribute(AttributeKey.LOGGED_USERS, loggedUsers);
        LOGGER.info("[{}] was removed from context", email);

    }


    public static String getLocale(HttpServletRequest req) {
        return (String)req.getSession().getAttribute("locale");
    }
}
