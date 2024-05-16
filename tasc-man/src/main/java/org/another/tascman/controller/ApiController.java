package org.another.tascman.controller;


import jakarta.validation.Valid;
import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.another.tascman.repository.AnderTaskRepository;
import org.another.tascman.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
@RequestMapping("/api")
public class ApiController {
    private TaskRepository taskRepository;
    private AnderTaskRepository anderTaskRepository;
    private TaskName taskName = new TaskName();

    public ApiController(TaskRepository taskRepository,
                         AnderTaskRepository anderTaskRepository) {
        this.taskRepository = taskRepository;
        this.anderTaskRepository = anderTaskRepository;
    }

    @GetMapping("/gTN")
    public Iterable<TaskName> getTaskName() {
        return taskRepository.findAll();
    }

    @GetMapping("/gAT")
    public Iterable<AnderTask> getAnderTask() {
        return anderTaskRepository.findAll();
    }

    @GetMapping("/gTAT")
    public List<AnderTask> getAnderAndTaskName(@RequestParam(value = "id") Long id) {
        return anderTaskRepository.findAllByTaskNameId(id);
    }

    @PostMapping("/pTN")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTaskName(@Valid @RequestBody TaskName taskName) {
        taskRepository.save(taskName);
    }

    @PostMapping("/pAT")
    @ResponseStatus(HttpStatus.CREATED)
    public void postAnderTask(@Valid @RequestBody AnderTask anderTask) {
        anderTaskRepository.save(anderTask);
    }

    @PatchMapping("/ptTN")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void patchTaskName(@RequestParam(value = "taskName") String taskName, @RequestParam(value = "newTaskName") String newTaskName) {
        taskRepository.updateTaskNameById(taskName, newTaskName);
    }

    @PatchMapping("/ptAT")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void patchAnderTask(@RequestParam(value = "id") Long id, @RequestParam(value = "nAT") String nAT) {
        anderTaskRepository.updateAnderTaskBySubtaskText(id, nAT);
    }

    @DeleteMapping("/dlTN")
    public void deleteTaskName(@RequestParam(value = "id") Long id) {
        anderTaskRepository.deleteByTaskNameId(id);
        taskRepository.deleteById(id);
    }

    @DeleteMapping("/dlAT")
    public void deleteAnderTask(@RequestParam(value = "id") Long id) {
        anderTaskRepository.deleteById(id);
    }

    @DeleteMapping("/dAllTN")
    public void deleteAllTaskName() {
        anderTaskRepository.deleteAll();
        taskRepository.deleteAll();
    }

    @DeleteMapping("/dAllAT")
    public void deleteAllAnderTask() {
        anderTaskRepository.deleteAll();
    }
}



