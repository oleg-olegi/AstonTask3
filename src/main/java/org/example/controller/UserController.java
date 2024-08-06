package org.example.controller;

import org.example.dto.UserDTO;
import org.example.exceptions.UserNotFoundException;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/users"))
public class UserController {
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        logger.info("UserController constructor");
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            logger.info("USER_CONTROLLER getUserById {}", userDTO.toString());
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> userDTO = userService.getAllUsers();
            logger.info("USER_CONTROLLER getAllUsers");
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            logger.info("USER_CONTROLLER createUser {}", userDTO.toString());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER createUser {}", userDTO.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        try {
            logger.info("USER_CONTROLLER updateUser {}", userDTO.toString());
            userService.updateUser(id, userDTO);
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER updateUser {}", userDTO.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            logger.info("USER_CONTROLLER deleteUser {}", id);
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER deleteUser {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
