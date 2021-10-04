package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Advertisement;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeletePhotoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(DeletePhotoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long photoId = Integer.parseInt(req.getParameter("photoId"));
        long advertId = Integer.parseInt(req.getParameter("advertId"));

        try {
            Advertisement advertisement = new Advertisement();
            advertisement.setId(advertId);
            Photo photo = Photo.of(advertisement);
            photo.setId(photoId);

            AdRepository.instOf().deletePhoto(photo);
            Files.delete(Path.of("/home/gh0st/images/" + photoId));
            resp.sendRedirect(req.getContextPath() + "/ad.do?id=" + advertId);
        } catch (Exception e) {
            logger.error("Exception when delete photo of advert", e);
            resp.sendRedirect(req.getContextPath() + "/ad.do?id=" + advertId);
        }
    }
}
