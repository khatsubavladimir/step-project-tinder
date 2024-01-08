package tinder;

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

public class LikedProfilesServlet extends HttpServlet {
    private UserSqlService service;

    public LikedProfilesServlet(UserSqlService service) {
        this.service = service;
    }

    public void addLikedProfile(int user, User profile) {
        service.addLike(user, profile.getId());
    } // user - це currentUser, profile це ID профілю


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentUser = 1; // треба десь слідкувати за цим, після створення процесу логіну
        Map<String, List<Profile>> data = new HashMap<>();
        data.put("likedProfiles", service.getLikedProfiles(currentUser));
        System.out.println("Liked profiles size: " + service.getLikedProfiles(currentUser).size());
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



