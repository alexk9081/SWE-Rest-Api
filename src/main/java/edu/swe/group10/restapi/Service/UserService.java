package edu.swe.group10.restapi.Service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.API.Model.User;

@Service
public class UserService {

  private Logger logger = AppLogger.getInstance().getLogger();

  public UserService() {
    connectDatabase();
  }

  private void connectDatabase() {
    //TODO
  }
  
  public User getUser(String nNumber) {
    logger.info("User ({}) was requested", nNumber);

    User result = new User("n01450313", "Alex Keo");
    return result;
  }
}
