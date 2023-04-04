package edu.swe.group10.restapi.API.Model;

import java.util.List;

public class NotecardSet {
  private String id;
  private String name;
  private boolean isPublic;
  private String nNumber;
  private String description;
  private List<Notecard> notecards;


  public NotecardSet(String id, String name, boolean isPublic, String nNumber, String description, List<Notecard> notecards) {
    this.id = id;
    this.name = name;
    this.isPublic = isPublic;
    this.nNumber = nNumber;
    this.description = description;
    this.notecards = notecards;
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

  public boolean isIsPublic() {
    return this.isPublic;
  }

  public boolean getIsPublic() {
    return this.isPublic;
  }

  public void setIsPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }

  public String getNNumber() {
    return this.nNumber;
  }

  public void setNNumber(String nNumber) {
    this.nNumber = nNumber;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Notecard> getNotecards() {
    return this.notecards;
  }

  public void setNotecards(List<Notecard> notecards) {
    this.notecards = notecards;
  }

}
