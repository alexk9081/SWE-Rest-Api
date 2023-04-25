package edu.swe.group10.restapi.API.Controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.API.Model.Notecard;
import edu.swe.group10.restapi.Service.NotecardService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@CrossOrigin(origins ={"https://main--cen4010-group-project.netlify.app/", "https://www.alexkeo.com/", "https://www.unfospreytools.com/"})
@RestController
public class NotecardController {

  private Logger logger;
  private NotecardService notecardService;

  public NotecardController(NotecardService notecardService) {
    logger = AppLogger.getInstance().getLogger();
    this.notecardService = notecardService;
  }

  @GetMapping("/notecard/get")
  public ResponseEntity<Notecard> getNotecard(@RequestParam String noteID, @RequestParam String setID) {
    Notecard notecard = notecardService.getNotecard(setID, noteID);

    if (notecard != null) {
      return new ResponseEntity<>(notecard, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(notecard, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/notecard/delete")
  public ResponseEntity<Boolean> deleteNotecard(@RequestParam String noteID, @RequestParam String setID) {
    int res = notecardService.deleteNotecard(setID, noteID);

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

  @PatchMapping("/notecard/update")
  public ResponseEntity<Boolean> updateNotecard(@RequestBody Notecard notecard) {
    // Check if it exists
    Notecard possibleCard = notecardService.getNotecard(notecard.getSetId(), notecard.getNoteId());
    if (possibleCard == null) {
      return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    int res = notecardService.updateNotecard(notecard.getAnswer(), notecard.getQuestion(), notecard.getNoteId(),
        notecard.getSetId());

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

  @PostMapping("/notecard/create")
  public ResponseEntity<Boolean> createNotecard(@RequestBody Notecard notecard) {
    logger.info("Creating notecard {}", notecard);
    // Check if it exists
    Notecard possibleCard = notecardService.getNotecard(notecard.getSetId(), notecard.getNoteId());
    if (possibleCard != null) {
      return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    int res = notecardService.createNotecard(notecard.getSetId(), notecard.getNoteId(), notecard.getQuestion(),
        notecard.getAnswer());

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
