package edu.swe.group10.restapi.API.Controller;

import java.util.List;

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

import edu.swe.group10.restapi.API.Model.PlannerTask;
import edu.swe.group10.restapi.Service.PlannerTaskService;

/**
 * This class defines the interface the frontend will use
 * This class will also handle the return values and calling functions
 */
@CrossOrigin(origins ={"http://localhost:3000", "https://main--cen4010-group-project.netlify.app/", "https://www.alexkeo.com/", "https://www.unfospreytools.com/"})
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
  public ResponseEntity<Boolean> deleteTask(@RequestParam String startDate, @RequestParam String endDate,
      @RequestParam String taskSubject, @RequestParam String userID) {
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
  public ResponseEntity<Boolean> createTask(@RequestBody PlannerTask task) {
    // Check if value already exists
    PlannerTask possibleTask = plannerTaskService.getSpecificPlannerTask(task.getUserID(), task.getStartDate(), task.getEndDate(), task.getTaskSubject());
    if(possibleTask != null) {
      return new ResponseEntity<>(false, HttpStatus.CONFLICT);
    }

    int res = plannerTaskService.createPlannerTask(task.getStartDate(), task.getEndDate(), task.getTaskSubject(),
        task.getUserID(), task.getDescription(), task.getAllDayTrigger(), task.getRepeatValue());

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

  @PatchMapping("/planner/update")
  public ResponseEntity<Boolean> updateTask(@RequestBody PlannerTask[] notecard) {
    PlannerTask oldTask = notecard[0];
    PlannerTask newTask = notecard[1];

    int res = plannerTaskService.updatePlannerTask(newTask.getStartDate(), newTask.getEndDate(), newTask.getTaskSubject(), oldTask.getStartDate(), oldTask.getEndDate(), oldTask.getTaskSubject(), newTask.getDescription(), newTask.getAllDayTrigger(), newTask.getRepeatValue(), newTask.getUserID());

    switch (res) {
      case 0:
        return new ResponseEntity<>(true, HttpStatus.OK);

        case 1:
      default:
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
