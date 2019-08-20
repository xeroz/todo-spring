package io.agileintelligence.projectboard.web;

import io.agileintelligence.projectboard.domain.ProjectTask;
import io.agileintelligence.projectboard.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @GetMapping("/all")
    public Iterable<ProjectTask> getAllPTs(){
        return projectTaskService.findAll();
    }

    @GetMapping("/pt_id")
    public ResponseEntity<?> getPTById(@PathVariable Long pt_id){
        ProjectTask projectTask = projectTaskService.findById(pt_id);
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> deleteProjectTask(@PathVariable Long id){
        projectTaskService.delete(id);
        return new ResponseEntity<String>("Project Task delete", HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addPTToBoard(@RequestBody ProjectTask projectTask, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError  error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        ProjectTask newPT =  projectTaskService.saveOrUpdateProjectTask(projectTask);
        return new ResponseEntity<ProjectTask>(newPT, HttpStatus.CREATED);
    }
}
