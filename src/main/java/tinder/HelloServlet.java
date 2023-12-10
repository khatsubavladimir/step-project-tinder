package tinder;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HelloServlet extends HttpServlet {
    private DAO<Profile> profileDao;
    private LikedProfilesServlet likedProfilesServlet;

    public HelloServlet(DAO<Profile> profileDao, LikedProfilesServlet likedProfilesServlet) {
        this.profileDao = profileDao;
        this.likedProfilesServlet = likedProfilesServlet;
    }

    public void addLikedProfile(HttpServletRequest request, HttpServletResponse response, Profile profile) {
        likedProfilesServlet.addLikedProfile(profile);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile currentProfile = profileDao.getNextProfile();

        if (currentProfile != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("currentProfile", currentProfile);
            data.put("likedProfiles", likedProfilesServlet.getLikedProfiles());
            data.put("request", req);
            Template template = FreeMarkerConfig.getInstance().getTemplate("like-page.html");

            try {
                PrintWriter writer = resp.getWriter();
                template.process(data, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.sendRedirect("/liked");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");
        Profile currentProfile = profileDao.getNextProfile();

        // Додавання уподобаного профайлу до LikedProfilesServlet
        if ("yes".equals(choice)) {
            addLikedProfile(req, resp, currentProfile);
        }

        // Перевірка, чи залишилися доступні профайли
        if (currentProfile != null) {
            // Якщо є доступні профайли, перенаправляємо на сторінку "/users"
            resp.sendRedirect(req.getContextPath() + "/users");
        } else {
            // Якщо доступні профайли закінчилися, перенаправляємо на сторінку "/liked"
            resp.sendRedirect(req.getContextPath() + "/liked");
        }
    }
}

