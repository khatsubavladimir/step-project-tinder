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
import java.util.List;
import java.util.Map;

public class LikedProfilesServlet extends HttpServlet {
    private DAO<Profile> profileDao;

    @Override
    public void init() throws ServletException {
        super.init();
        profileDao = new ProfileDao();
    }

    public void addLikedProfile(Profile profile) {
        profileDao.add(profile);
        System.out.println("Added liked profile: " + profile.getName());
    }

    public List<Profile> getLikedProfiles() {
        return profileDao.getAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("likedProfiles", getLikedProfiles());
        Template template = FreeMarkerConfig.getInstance().getTemplate("liked_profiles.ftl");

        try {
            PrintWriter writer = resp.getWriter();
            template.process(data, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}



