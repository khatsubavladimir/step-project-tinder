package tinder.db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConn {
  @SneakyThrows
  public static Connection create(String url, String username, String password) {
    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to create a database connection", e);
    }
  }

  @SneakyThrows
  public static Connection createFromURL(String jdbc_url) {
    try {
      return DriverManager.getConnection(jdbc_url);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to create a database connection", e);
    }
  }
}
