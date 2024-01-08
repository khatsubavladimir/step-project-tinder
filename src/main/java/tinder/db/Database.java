package tinder.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection mkConn() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://18.197.50.26:3306/users",
                "steptinder",
                "zaq1xsw2%F"
        );
    }

}
