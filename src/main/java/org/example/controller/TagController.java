package org.example.controller;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.example.exceptions.TagNotFoundException;
import org.example.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getTagById(@PathVariable long id) {
        try {
            tagService.getTag(id);
        } catch (TagNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getTag(id));
    }

    @PostMapping
    public ResponseEntity<?> addTag(@RequestBody TagDTO tagDTO) {
        try {
            tagService.addTag(tagDTO);
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDTO);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> updateTag(@PathVariable long id, @RequestBody TagDTO tagDTO) {
        try {
            tagService.updateTag(id, tagDTO);
        } catch (TagNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

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
