package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LogOutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().removeAttribute("user");
        } catch (Exception e) {
            logger.error("Exception when logout", e);
        }
        resp.sendRedirect(req.getContextPath() + "/main.do");
    }
}
