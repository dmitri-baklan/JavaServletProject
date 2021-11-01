package periodicals.controller.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import periodicals.model.entity.user.authority.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRes = (HttpServletResponse) servletResponse;
        HttpSession session = httpReq.getSession();

        String uri = httpReq.getRequestURI();

        String roleName = (String)session.getAttribute("role");
        if(Objects.isNull(roleName)) {
            roleName = Role.GUEST.name();
            session.setAttribute("role", roleName);
        }

        Role role = Role.valueOf(roleName);
        if(role.getEndpoints().stream().anyMatch(uri::matches)) {
            LOGGER.info("{} access confirmed:[{}]", role.name(), uri);
            filterChain.doFilter(httpReq, httpRes);
        }else {
            LOGGER.error("{} hasn't access for endpoint:[{}]", role.name(), uri);
            httpRes.sendRedirect("/welcome");
        }
    }

    @Override
    public void destroy() {

    }
}
