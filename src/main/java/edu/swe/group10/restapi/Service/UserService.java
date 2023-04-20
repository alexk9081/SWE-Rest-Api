package edu.swe.group10.restapi.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

  /**
   * Create's user.
   * 
   * @param nNumber
   *          the user's nNumber
   * @param name
   *          the user's username
   * @param url
   *          the user's profile picture url
   */
  public int createUser(String nNumber, String name, String url) {
    logger.info("Creating user with name: {}, nNumber: {}, img: {}", name, nNumber, url);

    try {
      PreparedStatement pstmt = conn
          .prepareStatement("INSERT INTO STUDENT (Name, N_Number, image_url) " + " VALUES (?, ?, ?)");

      pstmt.setString(1, name);
      pstmt.setString(2, nNumber);
      pstmt.setString(3, url);

      int rows = pstmt.executeUpdate();

      logger.info("Rows inserted: {}", rows);

      if (rows > 0) {
        logger.info("User {} creation sucess", nNumber);
        return 0;
      } else {
        logger.info("User {} creation failure", nNumber);
        return 1;
      }

    } catch (SQLException e) {
      logger.error("Creating user was unsucessful.");
      e.printStackTrace();

      return 2;
    }

  }

  /**
   * Gets all of user's info.
   * 
   * @param nNumber
   *          the user's nNumber
   * @return userObject the instance of the user's object
   */
  public User getUser(String nNumber) {
    logger.info("User with n-number {} requested", nNumber);
    User user = null;
    try {
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM STUDENT " + "WHERE N_Number = ?");

      pstmt.setString(1, nNumber);

      logger.info("Executing query for user {}", nNumber);
      ResultSet result = pstmt.executeQuery();

      String[] userArr = new String[3]; // stores user details

      if (result.next()) {
        logger.info("User {} has been found", nNumber);
        userArr[0] = result.getString("name");
        userArr[1] = result.getString("n_number");
        userArr[2] = result.getString("image_url");

        // Constructor's Order: nNumber, name, url
        user = new User(userArr[1], userArr[0], userArr[2]);
      }

    } catch (SQLException e) {
      logger.error("Getting user was unsucessful.");
      e.printStackTrace();
    }

    logger.info("Returning user {}", user);
    return user;
  }

  /**
   * Deletes the user.
   * 
   * @param nNumber
   *          the user's nNumber
   * @return
   */
  public int deleteUser(String nNumber) {
    logger.info("Deleting user {}", nNumber);
    try {
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM STUDENT " + "WHERE N_Number = ?");

      pstmt.setString(1, nNumber);

      int rows = pstmt.executeUpdate();

      logger.info("Rows deleted: {}", rows);

      if (rows > 0) {
        logger.info("Deleting user {} sucessful", nNumber);
        return 0;
      } else {
        logger.info("Deleting user {} failure", nNumber);
        return 1;
      }

    } catch (SQLException e) {
      logger.error("Deleting user was unsucessful.");
      e.printStackTrace();

      return 2;
    }
  }

  /**
   * Updates only the user's username.
   * 
   * @param nNumber
   *          the user's nNumber
   * @param newName
   *          the user's new username
   */
  public void updateUserName(String nNumber, String newName) {
    logger.info("Updating user {}, with new name: {}", nNumber, newName);
    try {
      PreparedStatement pstmt = conn.prepareStatement("UPDATE STUDENT " + "SET Name = ?" + "WHERE N_Number = ?");

      pstmt.setString(1, newName);
      pstmt.setString(2, nNumber);

      int rows = pstmt.executeUpdate();

      logger.info("Rows updated with new name: {}", rows);

    } catch (SQLException e) {
      logger.error("Updating user's name was unsucessful.");
      e.printStackTrace();
    }
  }

  /**
   * Update's only user's profile picture.
   * 
   * @param nNumber
   *          the user's nNumber
   * @param newURL
   *          the user's new profile picture
   */
  public void updateUserURL(String nNumber, String newURL) {
    logger.info("Updating user {}, with new url: {}", nNumber, newURL);
    try {
      PreparedStatement pstmt = conn.prepareStatement("UPDATE STUDENT " + "SET image_url = ?" + "WHERE N_Number = ?");
      pstmt.setString(1, newURL);
      pstmt.setString(2, nNumber);

      int rows = pstmt.executeUpdate();

      logger.info("Rows updated with new URL: {}", rows);

    } catch (SQLException e) {
      logger.error("Updating user's profile picture url was unsucessful.");
      e.printStackTrace();
    }
  }
}