package tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import tinder.db.ConnDetails;
import tinder.db.DbConn;
import tinder.db.DbSetup;

import java.io.File;
import java.sql.Connection;


public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        //Підключення до БД:
        //DbSetup.migrate(ConnDetails.url, ConnDetails.username, ConnDetails.password);
        //Connection conn = DbConn.create(ConnDetails.url, ConnDetails.username, ConnDetails.password);

        ProfileDao profileDao = new ProfileDao();
        LikedProfilesServlet likedProfilesServlet = new LikedProfilesServlet();
        handler.addServlet(new ServletHolder(new HelloServlet(profileDao, likedProfilesServlet)), "/users");
        handler.addServlet(new ServletHolder(likedProfilesServlet), "/liked");


        String resourceBase = new File("src/main/resources").getAbsolutePath();
        handler.setResourceBase(resourceBase);
        handler.addServlet(DefaultServlet.class, "/");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
