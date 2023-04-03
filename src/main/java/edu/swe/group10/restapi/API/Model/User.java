package edu.swe.group10.restapi.API.Model;

import edu.swe.group10.restapi.AppLogger;

public class User {
  private String nNumber;
  private String name;

  private AppLogger myLogger = AppLogger.getInstance();

  public User(String nNumber, String name) {
    this.nNumber = nNumber;
    this.name = name;
  }

  public String getnNumber() {
    return nNumber;
  }

  public void setnNumber(String nNumber) {
    if(nNumber.matches("^[nN][0-9]{8}$")){
      this.nNumber = nNumber;
    }
    else {
      myLogger.logger.error("N-Number input is in the incorrect format: {}", nNumber);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
