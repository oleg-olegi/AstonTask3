package org.example.controller;

import org.example.dto.TagDTO;
import org.example.exceptions.TagNotFoundException;
import org.example.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTags() {
        List<TagDTO> tags = new ArrayList<>();
        tags.add(new TagDTO(1L, "tag1"));
        tags.add(new TagDTO(2L, "tag2"));

        when(tagService.getAllTags()).thenReturn(tags);

        ResponseEntity<?> responseEntity = tagController.getAllTags();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tags, responseEntity.getBody());
    }

    @Test
    void testGetTagById() throws TagNotFoundException {
        TagDTO tagDTO = new TagDTO(1L, "tag1");

        when(tagService.getTag(1L)).thenReturn(tagDTO);

        ResponseEntity<?> responseEntity = tagController.getTagById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTagByIdNotFound() throws TagNotFoundException {
        when(tagService.getTag(1L)).thenThrow(new TagNotFoundException("Tag not found"));

        ResponseEntity<?> responseEntity = tagController.getTagById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testAddTag() throws TagNotFoundException {
        TagDTO tag = new TagDTO(1L, "tag1");

        doNothing().when(tagService).addTag(tag);

        ResponseEntity<?> responseEntity = tagController.addTag(tag);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(tag, responseEntity.getBody());
    }

    @Test
    void testAddTagBadRequest() throws TagNotFoundException {
        TagDTO tag = new TagDTO(1L, "tag1");

        doThrow(new TagNotFoundException("Tag already exists")).when(tagService).addTag(tag);

        ResponseEntity<?> responseEntity = tagController.addTag(tag);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Tag already exists", responseEntity.getBody());
    }

    @Test
    void testUpdateTag() throws TagNotFoundException {
        TagDTO tag = new TagDTO(1L, "updatedTag");

        doNothing().when(tagService).updateTag(1L, tag);

        ResponseEntity<?> responseEntity = tagController.updateTag(1L, tag);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTagNotFound() throws TagNotFoundException {
        TagDTO tag = new TagDTO(1L, "updatedTag");

        doThrow(new TagNotFoundException("Tag not found")).when(tagService).updateTag(1L, tag);

        ResponseEntity<?> responseEntity = tagController.updateTag(1L, tag);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteTag() throws TagNotFoundException {
        doNothing().when(tagService).deleteTag(1L);

        ResponseEntity<?> responseEntity = tagController.deleteTag(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteTagNotFound() throws TagNotFoundException {
        doThrow(new TagNotFoundException("Tag not found")).when(tagService).deleteTag(1L);

        ResponseEntity<?> responseEntity = tagController.deleteTag(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
