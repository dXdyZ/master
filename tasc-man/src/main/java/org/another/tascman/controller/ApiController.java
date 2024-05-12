package org.another.tascman.controller;


import jakarta.validation.Valid;
import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.another.tascman.repository.AnderTaskRepository;
import org.another.tascman.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/api")
public class ApiController {
    private TaskRepository taskRepository;
    private AnderTaskRepository anderTaskRepository;

    public ApiController(TaskRepository taskRepository, AnderTaskRepository anderTaskRepository) {
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

    @PostMapping("/pTN")
    public void postTaskName(@Valid @RequestBody TaskName taskName) {
        taskRepository.save(taskName);
    }

    @PostMapping("/pAT")
    @ResponseStatus(HttpStatus.CREATED)
    public void postAnderTask(@Valid @RequestBody AnderTask anderTask) {
        anderTaskRepository.save(anderTask);
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
