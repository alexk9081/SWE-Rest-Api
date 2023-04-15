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

import edu.swe.group10.restapi.API.Model.PlannerTask;
import edu.swe.group10.restapi.Service.PlannerTaskService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@RestController
public class PlannerTaskController {

  private PlannerTaskService plannerTaskService;

  public PlannerTaskController(PlannerTaskService plannerTaskService) {
    this.plannerTaskService = plannerTaskService;
  }

  @GetMapping("/planner/getall")
  public ResponseEntity<List<PlannerTask>> getAllTasks(@RequestParam String nNumber) {
    List<PlannerTask> tasks = plannerTaskService.getAllPlannerTasks(nNumber);

    if (tasks != null) {
      return new ResponseEntity<>(tasks, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(tasks, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/planner/delete")
  public ResponseEntity<Boolean> deleteUser(
      @RequestParam String startDate,
      @RequestParam String endDate,
      @RequestParam String taskSubject,
      @RequestParam String userID) {
    int res = plannerTaskService.deletePlannerTask(startDate, endDate, taskSubject, userID);

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

  @PostMapping("/planner/create")
  public ResponseEntity<Boolean> createUser(@RequestBody PlannerTask task) {
    //TODO check if value already exists

    //TODO Create is missing a lot of values
    int res = plannerTaskService.createPlannerTask(task.getStartDate(), task.getEndDate(), task.getTaskSubject(), task.getUserID());

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
}
