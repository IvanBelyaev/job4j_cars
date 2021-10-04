package ru.job4j.cars.servlets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Brand;
import ru.job4j.cars.models.Model;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class GetModelsByBrandServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(GetModelsByBrandServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Integer brandId = Integer.parseInt(req.getParameter("brand"));
            Brand brand = new Brand();
            brand.setId(brandId);
            List<Model> modelsByBrand = AdRepository.instOf().getModelsByBrand(brand);
            JSONArray models = new JSONArray(modelsByBrand);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("models", models);
            try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
                String out = jsonResponse.toString();
                writer.println(out);
                writer.flush();
            }
        } catch (Exception e) {
            logger.error("Exception when getting models from database", e);
        }
    }
}
