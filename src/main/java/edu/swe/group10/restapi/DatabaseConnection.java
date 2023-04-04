package edu.swe.group10.restapi;

import java.sql.Connection;

public class DatabaseConnection {
  private Connection conn;

  private static DatabaseConnection instance;

  private DatabaseConnection(){
    conn = null;
  }

  // static block initialization for exception handling
  static {
    try {
      instance = new DatabaseConnection();
    } catch (Exception e) {
      throw new NullPointerException("Exception occurred in creating singleton instance");
    }
  }

  public static DatabaseConnection getInstance() {
    return instance;
  }

  public Connection getConnection() {
    return conn;
  }
}
