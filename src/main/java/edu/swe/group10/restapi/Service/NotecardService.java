package edu.swe.group10.restapi.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.DatabaseConnection;
import edu.swe.group10.restapi.API.Model.Notecard;

/**
 * Manages the creation, editing, deleting, and getting of notecard information.
 */
@Service
public class NotecardService {

  private Logger logger;
  private Connection conn;

  public NotecardService() {
    logger = AppLogger.getInstance().getLogger();
    conn = DatabaseConnection.getInstance().getConnection();
  }

  /**
   * Creates notecard in database.
   * 
   * @param setID
   *          unique set ID
   * @param noteID
   *          unique notecard ID
   * @param question
   *          notecard's question
   * @param answer
   *          notecard's answer
   * 
   */
  public void createNotecard(String setID, String noteID, String question, String answer) {
    try {
      PreparedStatement pstmt = conn
          .prepareStatement("INSERT INTO NOTECARD (set_ID, notecard_id, question, answer) " + "VALUES (?, ?, ?, ?) ");
      pstmt.setString(1, setID);
      pstmt.setString(2, noteID);
      pstmt.setString(3, question);
      pstmt.setString(4, answer);

      int rows = pstmt.executeUpdate();

      logger.info("Rows inserted: {}", rows);
    } catch (SQLException e) {
      logger.error("Creating notecard was unsucessful.");
      e.printStackTrace();
    }

  }

  /**
   * Uses set ID and notecard ID to return a single notecard object. Returns null
   * if none found.
   * 
   * @param setID
   *          the set ID the notecard is in
   * @param noteID
   *          the notecard ID of the notecard
   * @return notecard an object instance of the notecard found, null if no
   *         notecard found
   */
  public Notecard getNotecard(String setID, String noteID) {
    // TODO Missing n number, partial key

    Notecard notecard = null;

    try {
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NOTECARD WHERE set_ID = ? AND notecard_id = ?");

      pstmt.setString(1, setID);
      pstmt.setString(2, noteID);

      ResultSet result = pstmt.executeQuery();

      String[] userArr = new String[4];

      if (result.next()) {
        userArr[0] = result.getString("Set_ID");
        userArr[1] = result.getString("Notecard_ID");
        userArr[2] = result.getString("Question");
        userArr[3] = result.getString("Answer");

        // Constructor's Order: notecard_id, question, answer, set_id

        notecard = new Notecard(userArr[1], userArr[2], userArr[3], userArr[0]);
      }

    } catch (SQLException e) {
      logger.error("Getting notecard was unsucessful.");
      e.printStackTrace();
    }

    return notecard;
  }

  /**
   * Uses set ID and notecard ID to delete a single notecard object.
   * 
   * @param setID
   *          the set ID the notecard is in
   * @param noteID
   *          the notecard ID of the notecard
   */
  public void deleteNotecard(String setID, String noteID) {

    try {
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NOTECARD WHERE set_ID = ? AND notecard_ID = ?");
      pstmt.setString(1, setID);
      pstmt.setString(2, noteID);

      int rows = pstmt.executeUpdate();

      logger.info("Rows deleted: {}", rows);
    } catch (SQLException e) {
      logger.error("Deleting notecard was unsucessful.");
      e.printStackTrace();
    }

  }

  /**
   * Uses set ID and notecard ID to update a single notecard object.
   * 
   * Provide all variables, even if one remains the same. If you don't it'll be
   * set to null.
   * 
   * @param answer
   *          the notecard's question
   * @param question
   *          the notecard's answer
   * @param setID
   *          the set ID the notecard is in
   * @param noteID
   *          the notecard ID of the notecard
   */
  public void updateNotecard(String answer, String question, String noteID, String setID) {
    try {
      PreparedStatement pstmt = conn.prepareStatement(
          "UPDATE NOTECARD " + "SET Question = ?, Answer = ?" + "WHERE notecard_ID = ? AND set_ID = ? ");
      pstmt.setString(1, question);
      pstmt.setString(2, answer);
      pstmt.setString(3, noteID);
      pstmt.setString(4, setID);

      int rows = pstmt.executeUpdate();
      logger.info("Rows updated: {}", rows);
    } catch (SQLException e) {
      logger.error("Updating notecard set was unsucessful.");
      e.printStackTrace();
    }
  }
}
