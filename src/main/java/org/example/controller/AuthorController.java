package org.example.controller;

import org.example.dto.AuthorDTO;
import org.example.exceptions.UserNotFoundException;
import org.example.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/users"))
public class AuthorController {
    private final AuthorService authorService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    public AuthorController(AuthorService authorService) {
        logger.info("UserController constructor");
        this.authorService = authorService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        try {
            AuthorDTO authorDTO = authorService.getUserById(id);
            logger.info("USER_CONTROLLER getUserById {}", authorDTO);
            return ResponseEntity.ok(authorDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<AuthorDTO> authorDTO = authorService.getAllUsers();
            logger.info("USER_CONTROLLER getAllUsers");
            return ResponseEntity.ok(authorDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.createUser(authorDTO);
            logger.info("USER_CONTROLLER createUser {}", authorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER createUser {}", authorDTO);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody AuthorDTO authorDTO) {
        try {
            logger.info("USER_CONTROLLER updateUser {}", authorDTO);
            authorService.updateUser(id, authorDTO);
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER updateUser {}", authorDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            logger.info("USER_CONTROLLER deleteUser {}", id);
            authorService.deleteUser(id);
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER deleteUser {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
