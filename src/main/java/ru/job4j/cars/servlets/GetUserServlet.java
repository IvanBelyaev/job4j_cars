package ru.job4j.cars.servlets;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetUserServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(GetUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getSession().getAttribute("user");
            if (user != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("username", user.getUsername());
                jsonResponse.put("phone", user.getPhone());
                jsonResponse.put("email", user.getEmail());
                jsonResponse.put("id", user.getId());
                try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
                    String out = jsonResponse.toString();
                    writer.println(out);
                    writer.flush();
                }
            }
        } catch (Exception e) {
            logger.error("Exception when check authentication", e);
        }
    }
}
