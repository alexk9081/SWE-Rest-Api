package edu.swe.group10.restapi.API.Model;

public class Notecard {
  private String setid;
  private String noteid;
  private String question;
  private String answer;
  
  public Notecard() {
    super();
  }

  public Notecard(String noteID, String question, String answer, String setID) {
    this.setid = setID;
    this.noteid = noteID;
    this.question = question;
    this.answer = answer;
  }

  public String getSetId() {
    return this.setid;
  }

  public void setSetId(String setid) {
    this.setid = setid;
  }

  public String getNoteId() {
    return this.noteid;
  }

  public void setNoteId(String noteid) {
    this.noteid = noteid;
  }

  public String getQuestion() {
    return this.question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return this.answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getSetid() {
    return this.setid;
  }

  public void setSetid(String setid) {
    this.setid = setid;
  }

  public String getNoteid() {
    return this.noteid;
  }

  public void setNoteid(String noteid) {
    this.noteid = noteid;
  }

  @Override
  public String toString() {
    return "{" +
      " setid='" + getSetId() + "'" +
      ", noteid='" + getNoteId() + "'" +
      ", question='" + getQuestion() + "'" +
      ", answer='" + getAnswer() + "'" +
      "}";
  }

}