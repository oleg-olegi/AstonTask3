package org.example.controller;

import org.example.dto.TagDTO;
import org.example.exceptions.TagNotFoundException;
import org.example.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible for handling HTTP requests related to {@link TagDTO} objects.
 * <p>
 * This controller provides endpoints for CRUD operations on Tag entities using the {@link TagService}.
 * </p>
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Constructor for TagController.
     *
     * @param tagService Service for handling Tag entities.
     */
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Retrieves all Tags.
     *
     * @return ResponseEntity containing a list of TagDTOs and a 200 OK status.
     */
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllTags() {
        List<TagDTO> tags = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    /**
     * Retrieves a Tag by its ID.
     *
     * @param id ID of the Tag to be retrieved.
     * @return ResponseEntity containing the TagDTO if found, or a 404 Not Found status if not.
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getTagById(@PathVariable long id) {
        try {
            tagService.getTag(id);
        } catch (TagNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getTag(id));
    }

    /**
     * Adds a new Tag.
     *
     * @param tagDTO Data Transfer Object representing the Tag to be added.
     * @return ResponseEntity with a 201 Created status if the Tag is successfully added, or a 400 Bad Request status if there are validation issues.
     */
    @PostMapping
    public ResponseEntity<?> addTag(@RequestBody TagDTO tagDTO) {
        try {
            tagService.addTag(tagDTO);
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDTO);
    }

    /**
     * Updates an existing Tag.
     *
     * @param id     ID of the Tag to be updated.
     * @param tagDTO Data Transfer Object containing updated Tag data.
     * @return ResponseEntity with a 201 Created status if the Tag is successfully updated, or a 404 Not Found status if the Tag with the specified ID is not found.
     */
    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateTag(@PathVariable long id, @RequestBody TagDTO tagDTO) {
        try {
            tagService.updateTag(id, tagDTO);
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a Tag by its ID.
     *
     * @param id ID of the Tag to be deleted.
     * @return ResponseEntity with a 204 No Content status if the Tag is successfully deleted, or a 404 Not Found status if the Tag with the specified ID is not found.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        try {
            tagService.deleteTag(id);
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
