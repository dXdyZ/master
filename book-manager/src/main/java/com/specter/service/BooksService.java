package com.specter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specter.entity.Books;
import com.specter.entity.Users;
import com.specter.entity.dto.BookDTO;
import com.specter.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.http.dsl.Http;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final UserService userService;
    private final FileService fileService;

    public Object getAllBooks(User user) {
        if (booksRepository.findByUserUsername(user.getUsername()).iterator().hasNext()) {
            return booksRepository.findByUserUsername(user.getUsername());
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    @Transactional(rollbackFor = IOException.class)
    public ResponseEntity<?> saveBooks(String booksJson, User user, MultipartFile file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Books books = objectMapper.readValue(booksJson, Books.class);
            books.setName(file.getOriginalFilename());
            if (null != books.getAuthor() && null != books.getName()) {
                Users users = userService.findByUsername(user.getUsername()).get();
                books.setUser(users);
                booksRepository.save(books);
                try {
                    if (file.getContentType().equals("application/pdf") || file.getContentType().equals("application/txt")) {
                        fileService.saveFile(file, books);
                    } else {
                        log.error("file format: {}", file.getContentType());
                        return new ResponseEntity<>("wrong file format", HttpStatus.BAD_REQUEST);
                    }
                } catch (IOException e) {
                    log.error("failed during file load: {}", e);
                    return new ResponseEntity<>("Failed during file loading", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("successful loading", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("incorrect data", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException exception) {
            log.error("failed mapping object in books: {}", exception);
            return new ResponseEntity<>("failed mapping object in books", HttpStatus.NOT_MODIFIED);
        }
    }

    public Object getBooksByName(String bookName) {
        if (booksRepository.findByName(bookName).isPresent()) {
            return booksRepository.findByName(bookName).get();
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    public Object searchAllBooks(String bookName) {
        List<Books> books = booksRepository.findAllByName(bookName);
        double rating = 0;
        if (!books.isEmpty()) {
            if (books.size() > 1) {
                for (int i = 0; i < books.size(); i++) {
                    rating += books.get(i).getRating();
                }
                BookDTO book = new BookDTO(books.get(0).getName(), books.get(0).getAuthor(), rating / books.size());
                books.clear();
                return book;
            } else return books.get(0);
        } else return new ResponseEntity<>("Книга отсутствует в библиотеке", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> searchAllBookAndSave(String bookName, User user) {
        Books books = booksRepository.findByName(bookName).stream().findFirst().get();
        if (books != null) {
            return new ResponseEntity<>(booksRepository.save(new Books().builder()
                    .name(books.getName())
                    .author(books.getAuthor()).build()), HttpStatus.CREATED);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public HttpStatus updateBook(String bookName, Books books) {
        if (booksRepository.findByName(bookName).isPresent()) {
            Books book = booksRepository.findByName(bookName).get();
            if (books.getName() != null) {
                book.setName(books.getName());
            }
            if (books.getAuthor() != null) {
                book.setAuthor(books.getAuthor());
            }
            if (books.getStatus() != null) {
                book.setStatus(books.getStatus());
            }
            booksRepository.save(book);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    public HttpStatus updateStatus(String bookName, String status) {
        if (booksRepository.findByName(bookName).isPresent()) {
            Books books = booksRepository.findByName(bookName).get();
            books.setStatus(status);
            booksRepository.save(books);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    @Transactional
    public ResponseEntity<?> unloadBookFile(String bookName, User user) {
        List<Books> books = booksRepository.findByUserUsernameOrderById(user.getUsername());
        Books book = books.stream()
                .filter(bookByName -> bookByName.getName().equals(bookName))
                .findFirst() //поиск первого совпадения
                .orElse(null);
        if (book != null) {
            return fileService.downloadFile(fileService.getFileByBookId(book.getId()).get().getId(), bookName);
        } else return ResponseEntity.notFound().build();
    }

    @Transactional
    public HttpStatus deleteBook(String bookName, User user) {
        if (booksRepository.findByName(bookName).get().getUser().getUsername().equals(user.getUsername())) {
            fileService.deleteBookFile(booksRepository.findByName(bookName).get().getId());
            booksRepository.deleteByName(bookName);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }
}
