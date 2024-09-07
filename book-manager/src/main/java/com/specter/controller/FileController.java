package com.specter.controller;

import com.specter.entity.Books;
import com.specter.repository.BooksRepository;
import com.specter.repository.UserRepository;
import com.specter.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final BooksRepository booksRepository;
    private final UserRepository userRepository;

    @PostMapping("/added")
    @Transactional
    public ResponseEntity<?> addedFile(@RequestBody MultipartFile file) {
        Books books = new Books().builder()
                .name(file.getOriginalFilename())
                .user(userRepository.findById(1L).get())
                .author("test")
                .createAt(new Date())
                .status("Reade")
                .build();
        booksRepository.save(books);
        try {
            fileService.saveFile(file, booksRepository.findById(1L).get());
        } catch (IOException e) {
            log.error("failed during file saved: {}", e);
            return new ResponseEntity<>("failed during file saved", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
