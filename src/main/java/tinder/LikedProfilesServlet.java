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
    private List<Profile> likedProfiles;

    public void setLikedProfiles(List<Profile> likedProfiles) {
        this.likedProfiles = likedProfiles;
    }

    public List<Profile> getLikedProfiles() {
        return likedProfiles;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("likedProfiles", likedProfiles);

        Template template = FreeMarkerConfig.getInstance().getTemplate("people-list.html");

        try {
            PrintWriter writer = resp.getWriter();
            template.process(data, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}



