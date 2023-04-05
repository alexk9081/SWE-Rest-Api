package edu.swe.group10.restapi.Service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.DatabaseConnection;
import edu.swe.group10.restapi.API.Model.User;

/**
 * This class will handle the implementation of database queries
 */
@Service
public class UserService {

  private Logger logger;
  private Connection conn;

  public UserService() {
    logger = AppLogger.getInstance().getLogger();
    conn = DatabaseConnection.getInstance().getConnection();
  }

  public User getUser(String nNumber) {
    logger.info("User ({}) was requested", nNumber);

    try {
      conn.prepareStatement("SELECT blah blah blah");
    } catch (Exception e) {
      logger.error("SQL EXCEPTION: {}", e.toString());
  }

    User result = new User(nNumber, "Alex Keo");
    return result;
  }
}
