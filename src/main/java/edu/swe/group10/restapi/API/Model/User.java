package edu.swe.group10.restapi.API.Model;

public class User {
  private String nNumber;
  private String name;

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
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
