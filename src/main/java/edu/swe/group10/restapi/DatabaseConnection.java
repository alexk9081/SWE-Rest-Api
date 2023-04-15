package edu.swe.group10.restapi;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;

public class DatabaseConnection {
  private Connection conn;
  private static Logger logger;

  private static DatabaseConnection instance;

  private DatabaseConnection() {

    String uid = "ffgemsct";
    String pword = System.getenv("JDBC_DATABASE_PASSWORD");
    String url = System.getenv("JDBC_DATABASE_URL");

    if (pword == null || pword.equals("")) {
      logger.error("No database password found");
      throw new NullPointerException("No database password found");
    }

    if (url == null || url.equals("")) {
      logger.error("No database url found");
      throw new NullPointerException("No database url found");
    }

    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection(url, uid, pword);
      logger.info("Database connection successfully established");
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("Exception occurred in creating database connection");
      throw new NullPointerException("Exception occurred in creating database connection");
    }
  }

  // static block initialization for exception handling
  static {
    try {
      logger = AppLogger.getInstance().getLogger();
      instance = new DatabaseConnection();
      logger.info("Database singleton instance successfully created");
    } catch (Exception e) {
      e.printStackTrace();
      throw new NullPointerException("Exception occurred in creating database singleton instance");
    }
  }

  public static DatabaseConnection getInstance() {
    return instance;
  }

  public Connection getConnection() {
    return conn;
  }
}
