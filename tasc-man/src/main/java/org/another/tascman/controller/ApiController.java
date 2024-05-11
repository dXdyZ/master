package org.another.tascman.controller;

import jakarta.validation.Valid;
import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.another.tascman.repository.AnderTaskRepository;
import org.another.tascman.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private TaskRepository taskRepository;
    private AnderTaskRepository anderTaskRepository;

    public ApiController(TaskRepository taskRepository, AnderTaskRepository anderTaskRepository) {
        this.taskRepository = taskRepository;
        this.anderTaskRepository = anderTaskRepository;
    }

    @GetMapping("/getTaskName")
    public Iterable<TaskName> getTaskName() {
        return taskRepository.findAll();
    }

    @GetMapping("/getAnderTask")
    public Iterable<AnderTask> getAnderTask() {
        return anderTaskRepository.findAll();
    }

    @PostMapping("/postTaskName")
    public Iterable<TaskName> postTaskName(@Valid @RequestBody TaskName taskName) {
        taskRepository.save(taskName);
        return taskRepository.findAll();
    }

    @PostMapping("/postAnderTaskName")
    public Iterable<AnderTask> postTaskName(@Valid @RequestBody AnderTask anderTask) {
        anderTaskRepository.save(anderTask);
        return anderTaskRepository.findAll();
    }
}
