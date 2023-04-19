package edu.swe.group10.restapi.API.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.API.Model.NotecardSet;
import edu.swe.group10.restapi.Service.NotecardSetService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@RestController
public class NotecardSetController {

  private NotecardSetService notecardSetService;

  public NotecardSetController(NotecardSetService notecardSetService) {
    this.notecardSetService = notecardSetService;
  }

  @GetMapping("/notecardset/get/set")
  public ResponseEntity<NotecardSet> getSet(@RequestParam String nNumber, @RequestParam String id) {
    NotecardSet set = notecardSetService.getNotecardSet(id, nNumber);

    if (set != null) {
      return new ResponseEntity<>(set, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(set, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/notecardset/get/public")
  public ResponseEntity<List<NotecardSet>> getPublicSets() {
    List<NotecardSet> sets = notecardSetService.getPublicNotecardSets();

    if (sets != null) {
      return new ResponseEntity<>(sets, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(sets, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/notecardset/get/personal")
  public ResponseEntity<List<NotecardSet>> getPersonalSets(@RequestParam String nNumber) {
    List<NotecardSet> sets = notecardSetService.getUsersNotecardSets(nNumber);

    if (sets != null) {
      return new ResponseEntity<>(sets, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(sets, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/notecardset/delete")
  public ResponseEntity<Boolean> deleteNotecardSet(@RequestParam String nNumber, @RequestParam String id) {
    int res = notecardSetService.deleteNotecardSet(id);

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

  @PostMapping("/notecardset/create")
  public ResponseEntity<Boolean> createNotecardSet(@RequestBody NotecardSet set) {
    // Check if it exists
    NotecardSet possibleSet = notecardSetService.getNotecardSet(set.getId(), set.getNNumber());
    if (possibleSet != null) {
      return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    int res = notecardSetService.createNotecardSet(set.getId(), set.getName(), set.getNNumber());;

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
