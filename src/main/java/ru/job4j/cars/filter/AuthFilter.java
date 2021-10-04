package ru.job4j.cars.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sresp;
        String uri = req.getRequestURI();
        if (
                uri.endsWith("brands.do")
                        || uri.endsWith("models.do")
                        || uri.endsWith("reg.do")
                        || uri.endsWith("login.do")
                        || uri.endsWith("auth.do")
                        || uri.endsWith("main.do")
                        || uri.endsWith("ad.do")
                        || uri.endsWith("getPhoto.do")
                        || uri.endsWith("getFilteredAds.do")
        ) {
            chain.doFilter(sreq, sresp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.do");
            return;
        }
        chain.doFilter(sreq, sresp);
    }

    @Override
    public void destroy() {
    }
}
