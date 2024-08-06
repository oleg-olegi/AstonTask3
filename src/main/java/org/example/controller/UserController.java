package org.example.controller;

import org.example.dto.UserDTO;
import org.example.exceptions.UserNotFoundException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(("/users"))
public class UserController {
    private final UserService userService;

    private static Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController(UserService userService) {
        System.out.println("UserController created");
        this.userService = userService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            logger.info("In User controller");
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
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
