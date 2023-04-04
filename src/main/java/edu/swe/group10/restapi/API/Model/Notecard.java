package edu.swe.group10.restapi.API.Model;

public class Notecard {
  private String id;
  private String name;
  private String question;
  private String answer;

  public Notecard(String id, String name, String question, String answer, String setID) {
    this.id = id;
    this.name = name;
    this.question = question;
    this.answer = answer;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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

}
