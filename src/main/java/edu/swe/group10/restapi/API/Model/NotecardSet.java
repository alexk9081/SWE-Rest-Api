package edu.swe.group10.restapi.API.Model;

import java.util.List;
import java.util.ArrayList;

public class NotecardSet {
  private String id;
  private String name;
  private boolean isPublic;
  private String nNumber;
  private String description;
  private String imageUrl;

  private List<Notecard> notecards;
  private User creator;

  public NotecardSet() {
    super();
  }

  public NotecardSet(String id, String name, boolean isPublic, String nNumber, String description,
      List<Notecard> notecards, User creator, String imageUrl) {
    this.id = id;
    this.name = name;
    this.isPublic = isPublic;
    this.nNumber = nNumber;
    this.description = description;
    this.notecards = notecards;
    this.creator = creator;
    this.imageUrl = imageUrl;
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

  public void setNotecards(ArrayList<Notecard> notecards) {
    this.notecards = notecards;
  }

  public User getCreator() {
    return this.creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", isPublic='" + isIsPublic() + "'" +
        ", nNumber='" + getNNumber() + "'" +
        ", description='" + getDescription() + "'" +
        ", imageUrl='" + getImageUrl() + "'" +
        ", notecards='" + getNotecards() + "'" +
        ", creator='" + getCreator() + "'" +
        "}";
  }
}