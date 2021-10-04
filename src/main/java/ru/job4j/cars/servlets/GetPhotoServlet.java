package ru.job4j.cars.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GetPhotoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(GetPhotoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("id");
            File downloadFile = new File("/home/gh0st/images/" + name);
            if (downloadFile.exists()) {
                resp.setContentType("application/octet-stream");
                resp.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
                try (FileInputStream stream = new FileInputStream(downloadFile)) {
                    resp.getOutputStream().write(stream.readAllBytes());
                }
            }
        } catch (Exception e) {
            logger.error("Exception when getting photo", e);
        }
    }
}
