package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String phone = req.getParameter("phone");
            String password = req.getParameter("password");

            User userByPhone = AdRepository.instOf().getUserByPhone(phone);
            if (userByPhone != null && userByPhone.getPassword().equals(password)) {
                req.getSession().setAttribute("user", userByPhone);
                resp.sendRedirect(req.getContextPath() + "/main.do");
            } else {
                doGet(req, resp);
            }
        } catch (Exception e) {
            logger.error("Exception when login user", e);
            req.getRequestDispatcher("/login.do").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signIn.html").forward(req, resp);
    }
}
