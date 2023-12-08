package tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        ProfileDao profileDao = new ProfileDao();
        LikedProfilesServlet likedProfilesServlet = new LikedProfilesServlet();
        handler.addServlet(new ServletHolder(new HelloServlet(profileDao, likedProfilesServlet)), "/users");
        handler.addServlet(new ServletHolder(likedProfilesServlet), "/liked");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
