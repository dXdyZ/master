package org.another.rollprojectspring.controller;


import org.another.rollprojectspring.model.User;
import org.another.rollprojectspring.repository.GrantRepository;
import org.another.rollprojectspring.repository.ProjectRepository;
import org.another.rollprojectspring.repository.UserRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiController {

    private UserRepo userRepo;
    private ProjectRepository projectRepository;
    private GrantRepository grantRepository;

    public ApiController(UserRepo userRepo, ProjectRepository projectRepository, GrantRepository grantRepository) {
        this.userRepo = userRepo;
        this.projectRepository = projectRepository;
        this.grantRepository = grantRepository;
    }


    @GetMapping("/users")
    public Iterable<User> allUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/project")
    public Object allProject() {
        return projectRepository.findAll();
    }


}
