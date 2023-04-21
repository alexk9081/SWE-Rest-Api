package edu.swe.group10.restapi.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.DatabaseConnection;
import edu.swe.group10.restapi.API.Model.PlannerTask;

/**
 * Manages the creation, editing, deleting, and getting of planner task information.
 */
@Service
public class PlannerTaskService {

  private Logger logger;
  private Connection conn;

  public PlannerTaskService() {
    logger = AppLogger.getInstance().getLogger();
    conn = DatabaseConnection.getInstance().getConnection();
  }

  /**
   * Creates a planner task.
   * 
   * @param startDate
   *          the start date of task
   * @param endDate
   *          the end date of task
   * @param taskSubject
   *          the task subject
   * @param userID
   *          the nNumber
   * @return
   * @return
   */
  public int createPlannerTask(String startDate, String endDate, String taskSubject, String userID, String description,
      boolean allDayTrigger, String repeatValue) {
    String allDayTriggerString = allDayTrigger ? "T" : "F";

    logger.info(
        "Creating planner task for {}, with info startDate: {}, endDate: {}, taskSubject: {}, allDay: {}, repeat: {}",
        userID, startDate, endDate, taskSubject, allDayTriggerString, repeatValue);
    try {
      PreparedStatement pstmt = conn.prepareStatement(
          "INSERT INTO PLANNER_TASK (User_ID, Start_Date, End_Date, Task_Subject, all_day_trigger, description, repeat_value) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?) ");
      pstmt.setString(1, userID);
      pstmt.setString(2, startDate);
      pstmt.setString(3, endDate);
      pstmt.setString(4, taskSubject);
      pstmt.setString(5, allDayTriggerString);
      pstmt.setString(6, description);
      pstmt.setString(7, repeatValue);

      int rows = pstmt.executeUpdate();

      logger.info("Rows inserted: {}", rows);

      return rows > 0 ? 0 : 1;
    } catch (SQLException e) {
      logger.error("Creating planner task was unsucessful.");
      e.printStackTrace();
      return 2;
    }

  }

  /**
   * Returns an arraylist of all of user's planner tasks using the user's nNumber.
   * Returns empty arraylist if no tasks.
   * 
   * @param nNumber
   *          the user's nNumber
   * @return arrayListTasksall of the user's planner tasks
   */
  public List<PlannerTask> getAllPlannerTasks(String nNumber) {
    logger.info("Getting all planner tasks for user {}", nNumber);

    PlannerTask task = null;
    List<PlannerTask> tasks = new ArrayList<>();

    try {
      PreparedStatement pstmt = conn.prepareStatement("SELECT* FROM PLANNER_TASK " + "WHERE User_ID = ?");

      pstmt.setString(1, nNumber);

      ResultSet result = pstmt.executeQuery();

      String[] userArr = new String[7];

      while (result.next()) {
        userArr[0] = result.getString("user_id");
        userArr[1] = result.getString("all_day_trigger");
        userArr[2] = result.getString("end_date");
        userArr[3] = result.getString("task_subject");
        userArr[4] = result.getString("description");
        userArr[5] = result.getString("repeat_value");
        userArr[6] = result.getString("start_date");

        boolean trigger = userArr[1].equals("T"); // if T, then 1 --- default is F, so 0

        // Constructor's Order: startDate, endDate, taskSubject, description, allDayTrigger, repeatValue, userID

        task = new PlannerTask(userArr[6], userArr[2], userArr[3], userArr[4], trigger, userArr[5], userArr[0]);
        tasks.add(task);
      }

    } catch (SQLException e) {
      logger.error("Getting all user's planner tasks was unsucessful.");
      e.printStackTrace();

      return null;
    }

    logger.info("Got {} tasks for user {}", tasks.size(), nNumber);
    return tasks;
  }

  /**
   * Uses parameter's to get a specific task.
   * 
   * Returns null if no task.
   * 
   * @param nNumber
   *          the user's nNumber
   * @param startDate
   *          the task's start date
   * @param endDate
   *          the tasks's end date
   * @param taskSubject
   *          the task's subject/name
   * @return plannerTask an instance of plannerTask, null if no task is found
   */
  public PlannerTask getSpecificPlannerTask(String nNumber, String startDate, String endDate, String taskSubject) {
    logger.info(
        "Getting planner task for {}, with info startDate: {}, endDate: {}, taskSubject: {}",
        nNumber, startDate, endDate, taskSubject);

    PlannerTask task = null;

    try {
      PreparedStatement pstmt = conn.prepareStatement(
          "SELECT * FROM PLANNER_TASK WHERE user_id = ? AND start_date = ? AND end_date = ? AND task_subject = ?");

      pstmt.setString(1, nNumber);
      pstmt.setString(2, startDate);
      pstmt.setString(3, endDate);
      pstmt.setString(4, taskSubject);

      ResultSet result = pstmt.executeQuery();

      String[] userArr = new String[7];

      if (result.next()) {
        userArr[0] = result.getString("user_id");
        userArr[1] = result.getString("all_day_trigger");
        userArr[2] = result.getString("end_date");
        userArr[3] = result.getString("task_subject");
        userArr[4] = result.getString("description");
        userArr[5] = result.getString("repeat_value");
        userArr[6] = result.getString("start_date");

        boolean trigger = userArr[1].equals("T"); // if T, then 1 --- default is F, so 0

        // Constructor's Order: startDate, endDate, taskSubject, description, allDayTrigger, repeatValue, userID

        task = new PlannerTask(userArr[6], userArr[2], userArr[3], userArr[4], trigger, userArr[5], userArr[0]);
      }

    } catch (SQLException e) {
      logger.error("Getting planner task was unsucessful.");
      e.printStackTrace();
    }

    return task;
  }

  /**
   * Uses parameters to delete a single planner task.
   * 
   * @param startDate
   *          the task's start date
   * @param endDate
   *          the tasks's end date
   * @param taskSubject
   *          the task's subject/name
   * @param userID
   *          the user's nNumber
   * @return
   */
  public int deletePlannerTask(String startDate, String endDate, String taskSubject, String userID) {
    logger.info(
        "Deleting planner task for {}, with info startDate: {}, endDate: {}, taskSubject: {}",
        userID, startDate, endDate, taskSubject);

    try {
      PreparedStatement pstmt = conn.prepareStatement(
          "DELETE FROM PLANNER_TASK WHERE user_id = ? AND start_date = ? AND end_date = ? AND task_subject = ?");
      pstmt.setString(1, userID);
      pstmt.setString(2, startDate);
      pstmt.setString(3, endDate);
      pstmt.setString(4, taskSubject);

      int rows = pstmt.executeUpdate();

      logger.info("Rows deleted: {}", rows);

      return rows > 0 ? 0 : 1;
    } catch (SQLException e) {
      logger.error("Deleting planner task was unsucessful.");
      e.printStackTrace();

      return 2;
    }

  }

  /**
   * Updates planner task. If labeled new__var and it hasn't changed, provide original or null string.
   * 
   * @param newStartDate
   *          the "new" start date, give original start date (or null string) if unchanged
   * @param newEndDate
   *          the "new" end date, give original end date (or null string) if unchanged
   * @param newTaskSubject
   *          the "new" task subject, give original task subject (or null string) if unchanged
   * @param startDate
   *          the original start date
   * @param endDate
   *          the original end date
   * @param taskSubject
   *          the original task subject
   * @param description
   *          the most current description
   * @param allDayTrigger
   *          the most current day trigger
   * @param repeatValue
   *          the most current repeat value
   * @param userID
   *          the user's nNumber
   * @return 
   */
  public int updatePlannerTask(String newStartDate,
      String newEndDate, String newTaskSubject, String startDate, String endDate, String taskSubject,
      String description, boolean allDayTrigger, String repeatValue, String userID) {
    logger.info(
        "Updating planner task for {}, with info startDate: {}, endDate: {}, taskSubject: {}",
        userID, startDate, endDate, taskSubject);

    String allDayTriggerString = allDayTrigger ? "T" : "F";

    if (newStartDate == null)
      newStartDate = startDate;
    if (newEndDate == null)
      newEndDate = endDate;
    if (newTaskSubject == null)
      newTaskSubject = taskSubject;

    try {
      PreparedStatement pstmt = conn.prepareStatement("UPDATE PLANNER_TASK "
          + "SET Start_date = ?, End_date = ?, All_day_Trigger = ?, Repeat_Value = ?, Description = ?, Task_Subject = ?"
          + "WHERE User_ID = ? AND Start_Date = ?  AND End_Date = ? AND Task_Subject = ?");

      pstmt.setString(1, newStartDate);
      pstmt.setString(2, newEndDate);
      pstmt.setString(3, allDayTriggerString);
      pstmt.setString(4, repeatValue);
      pstmt.setString(5, description);
      pstmt.setString(6, newTaskSubject);
      pstmt.setString(7, userID);
      pstmt.setString(8, startDate);
      pstmt.setString(9, endDate);
      pstmt.setString(10, taskSubject);

      int rows = pstmt.executeUpdate();
      logger.info("Rows updated: {}", rows);

      return rows > 0 ? 0 : 1;
    } catch (SQLException e) {
      logger.error("Updating planner task was unsucessful.");
      e.printStackTrace();

      return 1;
    }

  }
}
