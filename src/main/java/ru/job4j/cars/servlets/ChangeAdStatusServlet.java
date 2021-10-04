package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeAdStatusServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ChangeAdStatusServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int advertId = Integer.parseInt(req.getParameter("advertId"));
        try {
            boolean status = Boolean.parseBoolean(req.getParameter("status"));
            AdRepository.instOf().changeAdStatus(advertId, status);
            resp.sendRedirect(req.getContextPath() + "/ad.do?id=" + advertId);
        } catch (Exception e) {
            logger.error("Exception when change advert status", e);
            req.getRequestDispatcher("/newAd.html").forward(req, resp);
        }
    }
}
