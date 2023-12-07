package app;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import freemarker.template.*;

public class UsersServlet extends HttpServlet {
    private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

    UsersServlet() throws IOException {
        cfg.setDirectoryForTemplateLoading(new File(ResourcesOps.dirUnsafe("static/templates")));
    }

    @Override
    protected void doGet(HttpServletRequest rq, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();

        try (PrintWriter w = resp.getWriter()) {
            Template template = cfg.getTemplate("users.html");
            template.process(data, w);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

}

