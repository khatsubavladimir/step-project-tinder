package tinder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/like-page")
public class LikePageServlet extends HttpServlet {
    private ProfileDao profileDao = new ProfileDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Profile currentProfile = profileDao.getNextProfile();
        request.setAttribute("currentProfile", currentProfile);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/like-page.html");
        dispatcher.forward(request, response);
    }
}

