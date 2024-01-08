package tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import tinder.db.ConnDetails;
import tinder.db.Database;
import tinder.db.DbConn;
import tinder.db.DbSetup;
import java.io.File;
import java.sql.Connection;


public class ServerApp {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();

            // Ініціалізація всіх ДАО
            Connection conn = Database.mkConn();
            MessageSqlDAO mdao = new MessageSqlDAO(conn);
            MessageSqlService mservice = new MessageSqlService(mdao);
            UserSqlDAO udao = new UserSqlDAO(conn);
            UserSqlService uservice = new UserSqlService(udao);

            // Перевірка дієздатності підключення до БД
            uservice.hardRestart(ConnDetails.url, ConnDetails.username, ConnDetails.password); // якщо треба очистка БД!!!
            System.out.println("перевірка дієздатності, очікуваний результат:");
            System.out.println("Emi hello Sheldon Emi ");
            System.out.print(uservice.getByID(2).toProfile().getName() + " ");                        // Emi
            System.out.print(mservice.getMessages(2, 1).get(0).getMessage() + " ");                   // hello
            uservice.getLikedProfiles(3).forEach(x -> System.out.print(x.getName() + " "));       // Sheldon Emi
            System.out.println();

            // Підключення до БД:
            //DbSetup.migrate(ConnDetails.url, ConnDetails.username, ConnDetails.password);
            DbConn.create(ConnDetails.url, ConnDetails.username, ConnDetails.password);

        ProfileDao profileDao = new ProfileDao();
        LikedProfilesServlet likedProfilesServlet = new LikedProfilesServlet(uservice);
        TinderServlet tinderServlet = new TinderServlet(uservice, likedProfilesServlet);

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