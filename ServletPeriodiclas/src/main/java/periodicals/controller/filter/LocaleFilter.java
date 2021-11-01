package periodicals.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocaleFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class.getName());
    public static final String LANG = "lang";
    public static final String LOCALE = "locale";
    public static final String EN = "en";
    public static final String UA = "ua";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String langValue = request.getParameter(LANG);

        if(langValue == null && (String) req.getSession().getAttribute(LOCALE) == null){
            LOGGER.info("Setting default locale: {}", EN);
            req.getSession().setAttribute(LOCALE, EN);
        }
        else if(EN.equalsIgnoreCase(langValue) || UA.equalsIgnoreCase(langValue)){
            LOGGER.info("Setting locale: {}", langValue);
            req.getSession().setAttribute(LOCALE, langValue);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
