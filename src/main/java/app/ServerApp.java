package app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServerApp {

    // https://mvnrepository.com
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(new ServletHolder(new UsersServlet()), "/hello"); // http://localhost:8080/hello
        handler.addServlet(new ServletHolder(new UsersServlet()), "/users"); // http://localhost:8080/users

        server.setHandler(handler);
        server.start();
        server.join();
    }

}
