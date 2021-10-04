package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Advertisement;
import ru.job4j.cars.models.BodyType;
import ru.job4j.cars.models.Model;
import ru.job4j.cars.models.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAdvertisementServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(AddAdvertisementServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            int modelId = Integer.parseInt(req.getParameter("model"));
            String description = req.getParameter("description");
            int manufactureYear = Integer.parseInt(req.getParameter("year"));
            int price = Integer.parseInt(req.getParameter("price"));
            int bodyTypeId = Integer.parseInt(req.getParameter("bodyType"));
            User author = (User) req.getSession().getAttribute("user");

            Model model = AdRepository.instOf().getModelById(modelId);
            BodyType bodyType = AdRepository.instOf().getBodyTypeById(bodyTypeId);
            Advertisement ad = Advertisement.of(description, price, manufactureYear, model, bodyType, author);
            AdRepository.instOf().saveAd(ad);
            resp.sendRedirect(req.getContextPath() + "/lk.do");
        } catch (Exception e) {
            logger.error("Exception when create new ad", e);
            req.getRequestDispatcher("/newAd.html").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/newAd.html").forward(req, resp);
    }
}
