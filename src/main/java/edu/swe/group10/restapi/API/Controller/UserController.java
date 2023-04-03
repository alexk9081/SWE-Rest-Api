package edu.swe.group10.restapi.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.API.Model.User;
import edu.swe.group10.restapi.Service.UserService;

@RestController
public class UserController {
  
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user")
  public User getUser(@RequestParam String nNumber) {
    User user = userService.getUser(nNumber);
    if(user != null){
      return user;
    }

    return null;
  }
}
