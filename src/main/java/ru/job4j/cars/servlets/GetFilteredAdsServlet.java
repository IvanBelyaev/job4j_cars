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
import java.util.List;

public class GetFilteredAdsServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(GetFilteredAdsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            String priceFrom = req.getParameter("priceFrom");
            String priceTo = req.getParameter("priceTo");
            String yearFrom = req.getParameter("yearFrom");
            String yearTo = req.getParameter("yearTo");
            String bodyType = req.getParameter("bodyType");
            String withPhoto = req.getParameter("withPhoto");

            List<Advertisement> filteredAds = AdRepository.instOf().getFilteredAds(brand, model, priceFrom, priceTo, yearFrom, yearTo, bodyType, withPhoto);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject();

            JSONArray ads = new JSONArray();
            for (Advertisement ad : filteredAds) {
                JSONObject curAd = new JSONObject(ad);
                long[] photoIds = ad.getPhotos().stream().mapToLong(Photo::getId).toArray();
                curAd.put("photos", new JSONArray(photoIds));
                ads.put(curAd);
            }

            jsonResponse.put("ads", ads);
            try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
                String out = jsonResponse.toString();
                writer.println(out);
                writer.flush();
            }
        } catch (Exception e) {
            logger.error("Exception when getting ads with filters", e);
        }
    }
}
