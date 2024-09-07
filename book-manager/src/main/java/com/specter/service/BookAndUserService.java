package com.specter.service;

import com.specter.repository.BooksRepository;
import com.specter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookAndUserService {
    private final BooksRepository booksRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteUserAndBook(String username) {
        booksRepository.deleteAllByUserUsername(username);
        userRepository.deleteByUsername(username);
    }

}
