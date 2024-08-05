package org.example.controller;

import org.example.dto.UserDTO;
import org.example.exceptions.UserNotFoundException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            logger.info("In User controller");
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
