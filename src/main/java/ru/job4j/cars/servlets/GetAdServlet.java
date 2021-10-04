package ru.job4j.cars.servlets;

import org.json.JSONArray;
import org.json.JSONObject;
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
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class GetAdServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(GetAdServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Advertisement ad = AdRepository.instOf().getAdById(id);
            Set<Long> photoIds = new HashSet<>();
            for (Photo photo : ad.getPhotos()) {
                photoIds.add(photo.getId());
            }
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject(ad);
            jsonResponse.put("photos", new JSONArray(photoIds));
            try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
                String out = jsonResponse.toString();
                writer.println(out);
                writer.flush();
            }
        } catch (Exception e) {
            logger.error("Exception when getting an ad", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/advert.html").forward(req, resp);
    }
}
