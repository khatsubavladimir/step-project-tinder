package tinder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinderServlet extends HttpServlet {
    private DAO<Profile> profileDao;
    private LikedProfilesServlet likedProfilesServlet;

    public TinderServlet(DAO<Profile> profileDao, LikedProfilesServlet likedProfilesServlet) {
        this.profileDao = profileDao;
        this.likedProfilesServlet = likedProfilesServlet;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Profile> profiles = profileDao.getAll();
        Integer currentIndex = (Integer) req.getSession().getAttribute("currentIndex");
        if (currentIndex == null) {
            currentIndex = 0;
            req.getSession().setAttribute("currentIndex", currentIndex);
        }
        if (currentIndex < profiles.size()) {
            Profile currentProfile = profiles.get(currentIndex);
            Configuration cfg = FreeMarkerConfig.getInstance();
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("profiles", profiles);
            templateData.put("currentProfile", currentProfile);
            templateData.put("request", req);
            try {
                Template template = cfg.getTemplate("like-page.html");
                PrintWriter writer = resp.getWriter();
                template.process(templateData, writer);
            } catch (TemplateException e) {
                throw new ServletException("Error processing FreeMarker template", e);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/liked");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");

        if ("like".equals(choice) || "dislike".equals(choice)) {
            Integer currentIndex = (Integer) req.getSession().getAttribute("currentIndex");
            if (currentIndex != null) {
                if ("like".equals(choice)) {
                    Profile currentProfile = (currentIndex < profileDao.getAll().size())
                            ? profileDao.getAll().get(currentIndex)
                            : null;
                    if (currentProfile != null) {
                        likedProfilesServlet.addLikedProfile(currentProfile);
                        System.out.println("Liked profile: " + currentProfile.getName());
                    }
                }

                currentIndex++;
                req.getSession().setAttribute("currentIndex", currentIndex);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
