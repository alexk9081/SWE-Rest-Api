package edu.swe.group10.restapi.API.Model;

public class PlannerTask {
  private String startDate;
  private String endDate;
  private String taskSubject;
  private String description;
  private boolean allDayTrigger;
  private String repeatValue;
  private String userID;

  public PlannerTask(String startDate, String endDate, String taskSubject, String description, boolean allDayTrigger,
      String repeatValue, String userID) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.taskSubject = taskSubject;
    this.description = description;
    this.allDayTrigger = allDayTrigger;
    this.repeatValue = repeatValue;
    this.userID = userID;
  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return this.endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getTaskSubject() {
    return this.taskSubject;
  }

  public void setTaskSubject(String taskSubject) {
    this.taskSubject = taskSubject;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // Spicy
  public boolean isAllDayTrigger() {
    return this.allDayTrigger;
  }

  // McDouble code
  public boolean getAllDayTrigger() {
    return this.allDayTrigger;
  }

  public void setAllDayTrigger(boolean allDayTrigger) {
    this.allDayTrigger = allDayTrigger;
  }

  public String getRepeatValue() {
    return this.repeatValue;
  }

  public void setRepeatValue(String repeatValue) {
    this.repeatValue = repeatValue;
  }

  public String getUserID() {
    return this.userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }
}