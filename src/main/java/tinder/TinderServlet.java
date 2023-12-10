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
    private List<Profile> likedProfiles = new ArrayList<>();

    public TinderServlet(DAO<Profile> profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Profile> profiles = profileDao.getAllProfiles();

        // Отримайте індекс поточного профілю з сесії або встановіть його в нуль, якщо він ще не встановлений.
        Integer currentIndex = (Integer) req.getSession().getAttribute("currentIndex");
        if (currentIndex == null) {
            currentIndex = 0;
            req.getSession().setAttribute("currentIndex", currentIndex);
        }

        // Перевірка, чи індекс поточного профілю не перевищує загальну кількість профілів
        if (currentIndex < profiles.size()) {
            // Отримайте поточний профіль за індексом.
            Profile currentProfile = profiles.get(currentIndex);

            // Конфігурація FreeMarker
            Configuration cfg = FreeMarkerConfig.getInstance();

            // Дані для передачі в шаблон FreeMarker
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("profiles", profiles);
            templateData.put("currentProfile", currentProfile); // Додайте поточний профіль до моделі
            templateData.put("request", req); // Додайте об'єкт request до моделі

            // Використання FreeMarker для генерації HTML
            try {
                Template template = cfg.getTemplate("like-page.html"); // Змінено розширення файлу на .ftl
                PrintWriter writer = resp.getWriter();
                template.process(templateData, writer);
            } catch (TemplateException e) {
                throw new ServletException("Error processing FreeMarker template", e);
            }
        } else {
            // Якщо індекс перевищує кількість профілів, здійсніть переадресацію на сторінку "/liked"
            resp.sendRedirect(req.getContextPath() + "/liked");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String choice = req.getParameter("choice");

        if ("like".equals(choice) || "dislike".equals(choice)) {
            // Якщо обрано "Like" або "Dislike", оновіть індекс профілю
            Integer currentIndex = (Integer) req.getSession().getAttribute("currentIndex");
            if (currentIndex != null) {
                // Якщо обрано "Like", додайте поточний профіль до списку likedProfiles
                if ("like".equals(choice)) {
                    Profile currentProfile = (currentIndex < profileDao.getAllProfiles().size())
                            ? profileDao.getAllProfiles().get(currentIndex)
                            : null;
                    if (currentProfile != null) {
                        likedProfiles.add(currentProfile);
                        // Вивести ім'я лайкнутого профілю у консоль
                        System.out.println("Liked profile: " + currentProfile.getName());
                    }
                }

                currentIndex++; // Перенесено в цей рядок
                req.getSession().setAttribute("currentIndex", currentIndex);
            }
        }

        // Перенаправлення на головну сторінку для відображення нового профілю
        resp.sendRedirect(req.getContextPath() + "/users");
    }
}
