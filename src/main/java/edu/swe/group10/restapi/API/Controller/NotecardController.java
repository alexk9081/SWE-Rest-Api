package edu.swe.group10.restapi.API.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.API.Model.Notecard;
import edu.swe.group10.restapi.Service.NotecardService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@RestController
public class NotecardController {

  private NotecardService notecardService;

  public NotecardController(NotecardService notecardService) {
    this.notecardService = notecardService;
  }

  @GetMapping("/notecard/get")
  public ResponseEntity<Notecard> getNotecard(@RequestParam String nNumber) {
    // User user = userService.getUser(nNumber);

    // if (user != null) {
    // return new ResponseEntity<>(user, HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    // }

    // TODO Missing n number, partial key
    return null;
  }

  @DeleteMapping("/notecard/delete")
  public ResponseEntity<Boolean> deleteUser(@RequestParam String nNumber) {
    // int res = userService.deleteUser(nNumber);

    // switch (res) {
    // case 0:
    // return new ResponseEntity<>(true, HttpStatus.OK);

    // case 1:
    // return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    // case 2:
    // default:
    // return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // TODO Missing n number, partial key
    return null;
  }

  @PostMapping("/notecard/create")
  public ResponseEntity<Boolean> createNotecard(@RequestBody Notecard notecard) {
    // //Check if user exists
    // User possibleUser = userService.getUser(user.getnNumber());
    // if(possibleUser != null) {
    // return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    // }

    // int res = userService.createUser(user.getName(), user.getnNumber(), user.getImageUrl());

    // switch (res) {
    // case 0:
    // return new ResponseEntity<>(true, HttpStatus.OK);

    // case 1:
    // return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

    // case 2:
    // default:
    // return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // TODO Missing n number, partial key
    return null;
  }
}
