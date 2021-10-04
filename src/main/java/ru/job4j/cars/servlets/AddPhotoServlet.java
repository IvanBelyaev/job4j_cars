package ru.job4j.cars.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.models.Advertisement;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AddPhotoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(AddPhotoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int advertId = Integer.parseInt(req.getParameter("advertId"));
        req.getRequestDispatcher("/addPhoto.html?advertId=" + advertId).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int advertId = 0;
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("/home/gh0st/images/");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for(FileItem item : items) {
                if (item.isFormField()) {
                    if (item.getFieldName().equals("advertId")) {
                        advertId = Integer.parseInt(item.getString());
                        System.out.println(advertId);
                    }
                }
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    Advertisement advertisement = new Advertisement();
                    advertisement.setId(advertId);
                    Photo photo = Photo.of(advertisement);
                    AdRepository.instOf().savePhoto(photo);
                    File file = new File(folder + File.separator + photo.getId());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                }
            }
            resp.sendRedirect(req.getContextPath() + "/ad.do?id=" + advertId);
        } catch (Exception e) {
            logger.error("Exception when add photo", e);
            resp.sendRedirect(req.getContextPath() + "/lk.do");
        }
    }
}
