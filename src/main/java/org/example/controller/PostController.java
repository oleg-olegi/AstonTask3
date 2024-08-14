package org.example.controller;

import org.example.dto.PostDTO;
import org.example.exceptions.PostNotFoundException;
import org.example.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible for handling HTTP requests related to {@link PostDTO} objects.
 * <p>
 * This controller provides endpoints for CRUD operations on Post entities using the {@link PostService}.
 * </p>
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
     * Constructor for PostController.
     *
     * @param postService Service for handling Post entities.
     */
    public PostController(PostService postService) {
        logger.info("PostController constructor");
        this.postService = postService;
    }

    /**
     * Retrieves a Post by its ID.
     *
     * @param id ID of the Post to be retrieved.
     * @return ResponseEntity containing the PostDTO if found, or a 404 Not Found status if not.
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getPostById(@PathVariable("id") long id) {
        try {
            PostDTO postDTO = postService.getPostById(id);
            logger.info("Post_CONTROLLER getPostById {}", postDTO);
            return ResponseEntity.ok(postDTO);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves all Posts.
     *
     * @return ResponseEntity containing a list of PostDTOs if found, or a 404 Not Found status if the list is empty.
     */
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDTO> postDTOList = postService.getAllPosts();
            logger.info("Post_CONTROLLER getAllPosts");
            return ResponseEntity.ok(postDTOList);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Creates a new Post.
     *
     * @param postDTO Data Transfer Object representing the Post to be created.
     * @return ResponseEntity with a 201 Created status if the Post is successfully created, or a 400 Bad Request status if there are validation issues.
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        try {
            logger.info("Post_CONTROLLER BEFORE createPost {}", postDTO);
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            logger.error("Post_CONTROLLER createPost failed for {}", postDTO, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Updates an existing Post.
     *
     * @param id      ID of the Post to be updated.
     * @param postDTO Data Transfer Object containing updated Post data.
     * @return ResponseEntity with a 200 OK status if the Post is successfully updated, or a 404 Not Found status if the Post with the specified ID is not found.
     */
    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updatePost(@PathVariable("id") long id, @RequestBody PostDTO postDTO) {
        try {
            logger.info("Post_CONTROLLER updatePost {}", postDTO);
            postService.updatePost(id, postDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PostNotFoundException e) {
            logger.error("Post_CONTROLLER updatePost failed for {}", postDTO, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a Post by its ID.
     *
     * @param id ID of the Post to be deleted.
     * @return ResponseEntity with a 204 No Content status if the Post is successfully deleted, or a 404 Not Found status if the Post with the specified ID is not found.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") long id) {
        try {
            logger.info("Post_CONTROLLER deletePost {}", id);
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (PostNotFoundException e) {
            logger.error("Post_CONTROLLER deletePost failed for ID {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
