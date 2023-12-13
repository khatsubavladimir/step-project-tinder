package tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import tinder.db.ConnDetails;
import tinder.db.DbConn;
import tinder.db.DbSetup;
import java.io.File;


public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        // Підключення до БД:
        DbSetup.migrate(ConnDetails.url, ConnDetails.username, ConnDetails.password);
        DbConn.create(ConnDetails.url, ConnDetails.username, ConnDetails.password);

        ProfileDao profileDao = new ProfileDao();
        LikedProfilesServlet likedProfilesServlet = new LikedProfilesServlet();
        TinderServlet tinderServlet = new TinderServlet(profileDao, likedProfilesServlet);

        MessagesServlet messagesServlet = new MessagesServlet();


        SessionHandler sessionHandler = new SessionHandler();
        handler.setHandler(sessionHandler);

        handler.addServlet(new ServletHolder(tinderServlet), "/users");
        handler.addServlet(new ServletHolder(likedProfilesServlet), "/liked");
        handler.addServlet(LoginServlet.class, "/login");
        handler.addServlet(new ServletHolder(messagesServlet), "/messages/*");


        String resourceBase = new File("src/main/resources").getAbsolutePath();
        handler.setResourceBase(resourceBase);
        handler.addServlet(DefaultServlet.class, "/");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}