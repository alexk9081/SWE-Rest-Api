package edu.swe.group10.restapi.Service;

import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.API.Model.User;

@Service
public class UserService {

  public UserService() {
    connectDatabase();
  }

  private void connectDatabase() {

  }
  
  public User getUser(String nNumber) {
    User result = new User("n01450313", "Alex Keo");
    return result;
  }
}
