package tinder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class LikePageServlet extends HttpServlet {
    private ProfileDao profileDao;
    private HelloServlet helloServlet;

    public LikePageServlet(ProfileDao profileDao, HelloServlet helloServlet) {
        this.profileDao = profileDao;
        this.helloServlet = helloServlet;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Profile currentProfile = profileDao.getNextProfile();
        request.setAttribute("currentProfile", currentProfile);

        // Відображення файлу "people-list.html"
        request.getRequestDispatcher("people-list.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choice = request.getParameter("choice");
        Profile currentProfile = profileDao.getNextProfile();

        // Додаємо уподобаний профайл до списку
        if ("like".equals(choice)) {
            helloServlet.addLikedProfile(request, response, currentProfile);
        }

        // Залишаємо користувача на сторінці "/users" після натискання кнопки
        response.sendRedirect(request.getContextPath() + "/users");
    }
}
