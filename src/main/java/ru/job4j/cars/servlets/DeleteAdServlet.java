package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DeleteAdServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeleteAdServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int advertId = Integer.parseInt(req.getParameter("advertId"));
            List<Photo> photosOfAd = AdRepository.instOf().getPhotosOfAd(advertId);
            AdRepository.instOf().deleteAd(advertId);
            for (Photo photo : photosOfAd) {
                Files.delete(Path.of("/home/gh0st/images/" + photo.getId()));
            }
            resp.sendRedirect(req.getContextPath() + "/lk.do");
        } catch (Exception e) {
            logger.error("Exception when delete advert", e);
            resp.sendRedirect(req.getContextPath() + "/lk.do");
        }
    }
}
