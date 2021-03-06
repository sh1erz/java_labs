package controller.util.filter;

import controller.util.constants.Page;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static controller.util.constants.Attribute.USER_ADMIN;
import static controller.util.constants.Attribute.USER_DOCTOR;

@WebFilter("/index.jsp")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HttpSession session = httpRequest.getSession(false);
        RequestDispatcher dispatcher;
        if (session == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (session.getAttribute(USER_DOCTOR.getAttribute()) != null){
            dispatcher = servletRequest.getRequestDispatcher(Page.DOCTOR_MAIN.getPage());
            dispatcher.forward(servletRequest, servletResponse);
        }else if (session.getAttribute(USER_ADMIN.getAttribute()) != null){
            dispatcher = servletRequest.getRequestDispatcher(Page.ADMIN_MAIN.getPage());
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        dispatcher.forward(servletRequest, servletResponse);
    }


}
