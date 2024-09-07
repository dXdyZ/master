package com.specter.service;

import com.specter.entity.Role;
import com.specter.entity.Users;
import com.specter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final UserRepository userRepository;
    private final BookAndUserService booksAndService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CodeService codeService;

    private static HashMap<String, Users> codeAdnUser = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(users -> new User(
                        users.getUsername(),
                        users.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(users.getRole().name()))
                )).orElseThrow(() -> new UsernameNotFoundException("Failed user " + username));
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Users> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public ResponseEntity<?> savedUser(Users users) {
        if (!users.getUsername().equals(userRepository.findByUsername(users.getUsername()))) {
            String code = codeService.getCode();
            codeAdnUser.put(code, new Users().builder()
                                    .username(users.getUsername())
                                    .email(users.getEmail())
                                    .password(passwordEncoder.encode(users.getPassword()))
                                    .role(Role.ROLE_USER)
                                    .createAt(new Date()).build());
            log.info("user generated: {}", codeAdnUser.get(code).toString());
            emailService.sendEmail(users.getEmail(), "Код поддтверждения", code);
            log.info("code: {}", code);

            //Планирования удаление элемента через 10 минут
            ScheduledFuture<?> scheduledTask = scheduler.schedule(() -> {
                removeCode(code);
            }, 10, TimeUnit.MINUTES);

            return new ResponseEntity<>("Поддтвердите код по этой ссылку '/user/examinCode?code=****'.\nСрок действия кода истечет через 10 минут", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Имя пользователя уже занято ", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> examinationCode(String examinationCode) {
        log.info("code and user", codeAdnUser.get(examinationCode).toString(), examinationCode);
        if (codeService.validationCode(examinationCode)) {
            userRepository.save(codeAdnUser.get(examinationCode));
            return new ResponseEntity<>("Вы зарегестированны ", HttpStatus.CREATED);
        } else return new ResponseEntity<>("Не правильный код попробуйте еще раз ", HttpStatus.BAD_REQUEST);
    }


    public Object updateUserData(Users users, User user) {
        if (users.getUsername().equals(user.getUsername())) {
            Users uppUser = userRepository.findByUsername(user.getUsername()).get();
            if (users.getUsername() != null) uppUser.setUsername(users.getUsername());
            if (users.getPassword() != null) uppUser.setPassword(users.getPassword());
            if (users.getEmail() != null) uppUser.setEmail(users.getEmail());
            emailService.sendEmail(users.getEmail(), "Изменение данных аккаунта", "Уважаемый пользователь данные вашего аккаунта были измены");
            return HttpStatus.OK;
        } else {
            return "Вы не авторизованны под этим пользователем";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public Iterable<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> setUserRole(String username, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            if (role.equals("ADMIN")) {
                userRepository.updateRoleByUsername(username, Role.ROLE_ADMIN);
            } else if (role.equals("USER")) {
                userRepository.updateRoleByUsername(username, Role.ROLE_USER);
            } else return new ResponseEntity<>("Такой роли нет, или она еще не добавлена", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Чтото отвалилось вот логи", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public HttpStatus deleteUserByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            booksAndService.deleteUserAndBook(username);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }
    }

    private void removeCode(String code) {
        codeAdnUser.remove(code);
    }
}
