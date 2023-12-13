package tinder;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/messages/*")
public class MessagesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Отримуємо ідентифікатор повідомлення з URL
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Отримуємо ідентифікатор повідомлення з URL
        String messageId = pathParts[1];

        // Передаємо дані повідомлення у шаблон
        Map<String, Object> data = new HashMap<>();
        data.put("messageId", messageId);

        // Отримуємо шаблон і виводимо його
        Template template = FreeMarkerConfig.getInstance().getTemplate("chat.html");
        try {
            PrintWriter writer = response.getWriter();
            template.process(data, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}