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


/**
 * Controller class responsible for handling HTTP requests related to {@link AuthorDTO} objects.
 * <p>
 * This controller provides endpoints for CRUD operations on Author entities using the {@link AuthorService}.
 * </p>
 */
@RestController
@RequestMapping("/users")
public class AuthorController {

    private final AuthorService authorService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    /**
     * Constructor for AuthorController.
     *
     * @param authorService Service for handling Author entities.
     */
    public AuthorController(AuthorService authorService) {
        logger.info("AuthorController constructor");
        this.authorService = authorService;
    }

    /**
     * Retrieves an Author by its ID.
     *
     * @param id ID of the Author to be retrieved.
     * @return ResponseEntity containing the AuthorDTO if found, or a 404 Not Found status if not.
     */
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

    /**
     * Retrieves all Authors.
     *
     * @return ResponseEntity containing a list of AuthorDTOs if found, or a 404 Not Found status if the list is empty.
     */
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<AuthorDTO> authorDTOList = authorService.getAllUsers();
            logger.info("USER_CONTROLLER getAllUsers");
            return ResponseEntity.ok(authorDTOList);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Creates a new Author.
     *
     * @param authorDTO Data Transfer Object representing the Author to be created.
     * @return ResponseEntity with a 201 Created status if the Author is successfully created, or a 400 Bad Request status if there are validation issues.
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.createUser(authorDTO);
            logger.info("USER_CONTROLLER createUser {}", authorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            logger.error("USER_CONTROLLER createUser failed for {}", authorDTO, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Updates an existing Author.
     *
     * @param id        ID of the Author to be updated.
     * @param authorDTO Data Transfer Object containing updated Author data.
     * @return ResponseEntity with a 201 Created status if the Author is successfully updated, or a 404 Not Found status if the Author with the specified ID is not found.
     */
    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody AuthorDTO authorDTO) {
        try {
            logger.info("USER_CONTROLLER updateUser {}", authorDTO);
            authorService.updateUser(id, authorDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER updateUser failed for {}", authorDTO, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes an Author by its ID.
     *
     * @param id ID of the Author to be deleted.
     * @return ResponseEntity with a 204 No Content status if the Author is successfully deleted, or a 404 Not Found status if the Author with the specified ID is not found.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            logger.info("USER_CONTROLLER deleteUser {}", id);
            authorService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            logger.error("USER_CONTROLLER deleteUser failed for ID {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
