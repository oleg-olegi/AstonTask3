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

@RestController
@RequestMapping(("/posts"))
public class PostController {

    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    public PostController(PostService postService) {
        logger.info("PostController constructor");
        this.postService = postService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getPostById(@PathVariable("id") long id) {
        try {
            PostDTO PostDTO = postService.getPostById(id);
            logger.info("Post_CONTROLLER getPostById {}", PostDTO.toString());
            return ResponseEntity.ok(PostDTO);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostDTO> PostDTO = postService.getAllPosts();
            logger.info("Post_CONTROLLER getAllPosts");
            return ResponseEntity.ok(PostDTO);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        try {
            logger.info("Post_CONTROLLER BEFORE createPost {}", postDTO.toString());
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PostNotFoundException e) {
            logger.error("Post_CONTROLLER createPost {}", postDTO.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updatePost(@PathVariable("id") long id, @RequestBody PostDTO postDTO) {
        try {
            logger.info("Post_CONTROLLER updatePost {}", postDTO.toString());
            postService.updatePost(id, postDTO);
        } catch (PostNotFoundException e) {
            logger.error("Post_CONTROLLER updatePost {}", postDTO.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") long id) {
        try {
            logger.info("Post_CONTROLLER deletePost {}", id);
            postService.deletePost(id);
        } catch (PostNotFoundException e) {
            logger.error("Post_CONTROLLER deletePost {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
