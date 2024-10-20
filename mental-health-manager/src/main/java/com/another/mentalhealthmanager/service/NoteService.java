package com.another.mentalhealthmanager.service;

import com.another.mentalhealthmanager.entity.user.Users;
import com.another.mentalhealthmanager.repository.NotesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class NoteService {
    private final NotesRepository notesRepository;
    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public NoteService(NotesRepository notesRepository, UserService userService) {
        this.notesRepository = notesRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> getAllNoteForUser(Principal principal) {
        Optional<Users> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) {
            return ResponseEntity.ok(notesRepository.findAllByUsersId(user.get().getId()));
        } return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
