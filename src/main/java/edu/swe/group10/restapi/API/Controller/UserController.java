package edu.swe.group10.restapi.API.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.API.Model.User;
import edu.swe.group10.restapi.Service.UserService;


/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@RestController
public class UserController {
  
  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user")
  public User getUser(@RequestParam String nNumber) {
    User user = userService.getUser(nNumber);
    
    return user;
  }
}
