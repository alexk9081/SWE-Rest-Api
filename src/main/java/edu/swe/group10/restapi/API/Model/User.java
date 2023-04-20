package edu.swe.group10.restapi.API.Model;

import org.slf4j.Logger;

import edu.swe.group10.restapi.AppLogger;

public class User {
  private String nNumber;
  private String name;
  private String imageUrl;

  private Logger logger = AppLogger.getInstance().getLogger();

  public User() {
    super();
  }

  public User(String nNumber, String name, String url) {
    this.nNumber = nNumber;
    this.name = name;
    this.imageUrl = url;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getnNumber() {
    return nNumber;
  }

  public void setnNumber(String nNumber) {
    if (nNumber.matches("^[nN]\\d{8}$")) {
      this.nNumber = nNumber;
    } else {
      logger.error("N-Number input is in the incorrect format: {}", nNumber);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "{" +
        " nNumber='" + getnNumber() + "'" +
        ", name='" + getName() + "'" +
        ", imageUrl='" + getImageUrl() + "'" +
        "}";
  }

}