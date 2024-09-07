package com.specter.controller;

import com.specter.entity.Books;
import com.specter.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/book")
@RequiredArgsConstructor
public class BooksController {
    private final BooksService booksService;

    @GetMapping("/all")
    public Object getAllBooks(@AuthenticationPrincipal User user) {
        return booksService.getAllBooks(user);
    }

    @PostMapping("/added")
    public ResponseEntity<?> savedBooks(@RequestParam("book") String bookJson,
                                        @RequestParam("file") MultipartFile file,
                                        @AuthenticationPrincipal User user) {
        return booksService.saveBooks(bookJson, user, file);
    }

    @GetMapping("/downloadBook")
    public ResponseEntity<?> downloadBook(@RequestParam(name = "bookName") String bookName,
                                          @AuthenticationPrincipal User user) {
        return booksService.unloadBookFile(bookName, user);
    }

    @PatchMapping("/{bookName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateBook(@PathVariable("bookName") String bookName,
                           @RequestBody Books books) {
        booksService.updateBook(bookName, books);
    }

    @GetMapping("/search/{name}")
    public Object searchBook(@PathVariable("name") String name) {
        return booksService.searchAllBooks(name);
    }

    @PatchMapping("/{bookNameStatus}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateStatusBook(@PathVariable("bookNameStatus") String bookName,
                                 @RequestParam(name = "status") String status) {
        booksService.updateStatus(status, bookName);
    }

    @DeleteMapping("/{bookDel}")
    public HttpStatus deleteBook(@PathVariable("bookDel") String bookDel,
                                 @AuthenticationPrincipal User user) {
        return booksService.deleteBook(bookDel, user);
    }
}
