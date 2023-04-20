package edu.swe.group10.restapi.API.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.API.Model.User;
import edu.swe.group10.restapi.Service.UserService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@CrossOrigin
@RestController
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/get")
  public ResponseEntity<User> getUser(@RequestParam String nNumber) {
    User user = userService.getUser(nNumber);

    if (user != null) {
      return new ResponseEntity<>(user, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/user/delete")
  public ResponseEntity<Boolean> deleteUser(@RequestParam String nNumber) {
    int res = userService.deleteUser(nNumber);

    switch (res) {
      case 0:
        return new ResponseEntity<>(true, HttpStatus.OK);

      case 1:
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

      case 2:
      default:
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/user/create")
  public ResponseEntity<Boolean> createUser(@RequestBody User user) {
    //Check if user exists
    User possibleUser = userService.getUser(user.getnNumber());
    if(possibleUser != null) {
      return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }


    int res = userService.createUser(user.getnNumber(), user.getName(), user.getImageUrl());

    switch (res) {
      case 0:
        return new ResponseEntity<>(true, HttpStatus.OK);

      case 1:
        return new ResponseEntity<>(false, HttpStatus.CONFLICT);

      case 2:
      default:
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
