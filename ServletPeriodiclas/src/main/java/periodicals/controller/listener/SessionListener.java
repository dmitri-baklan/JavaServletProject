package periodicals.controller.listener;

import java.util.HashSet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import periodicals.util.AttributeKey;


public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class.getName());
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        @SuppressWarnings("unchecked")
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent.getSession()
                .getServletContext().getAttribute(AttributeKey.LOGGED_USERS);
        String userName = (String) httpSessionEvent.getSession().getAttribute("email");
        loggedUsers.remove(userName);
        httpSessionEvent.getSession().setAttribute(AttributeKey.LOGGED_USERS, loggedUsers);
        LOGGER.info("[{}] removed from logged users set", userName);
    }

}
