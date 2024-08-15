package org.example.controller;

import org.example.dto.PostDTO;
import org.example.exceptions.PostNotFoundException;
import org.example.service.PostService;
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

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPostById() throws PostNotFoundException {
        PostDTO postDTO = new PostDTO(1L, "Post Title", "Post Content", 1L);

        when(postService.getPostById(1L)).thenReturn(postDTO);

        ResponseEntity<?> responseEntity = postController.getPostById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(postDTO, responseEntity.getBody());
    }

    @Test
    void testGetPostByIdNotFound() throws PostNotFoundException {
        when(postService.getPostById(1L)).thenThrow(new PostNotFoundException("Post not found"));

        ResponseEntity<?> responseEntity = postController.getPostById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllPosts() throws PostNotFoundException {
        List<PostDTO> posts = new ArrayList<>();
        posts.add(new PostDTO(1L, "Post Title 1", "Post Content 1",1L));
        posts.add(new PostDTO(2L, "Post Title 2", "Post Content 2", 2L));

        when(postService.getAllPosts()).thenReturn(posts);

        ResponseEntity<?> responseEntity = postController.getAllPosts();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(posts, responseEntity.getBody());
    }

    @Test
    void testGetAllPostsNotFound() throws PostNotFoundException {
        when(postService.getAllPosts()).thenThrow(new PostNotFoundException("Posts not found"));

        ResponseEntity<?> responseEntity = postController.getAllPosts();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreatePost() throws PostNotFoundException {
        PostDTO postDTO = new PostDTO(1L, "New Post Title", "New Post Content", 1L);

        doNothing().when(postService).createPost(postDTO);

        ResponseEntity<?> responseEntity = postController.createPost(postDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testCreatePostBadRequest() throws PostNotFoundException {
        PostDTO postDTO = new PostDTO(1L, null, null, 1L);

        doThrow(new IllegalArgumentException("Post fields cannot be empty")).when(postService).createPost(postDTO);

        ResponseEntity<?> responseEntity = postController.createPost(postDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatePost() throws PostNotFoundException {
        PostDTO postDTO = new PostDTO(1L, "Updated Post Title", "Updated Post Content", 1L);

        doNothing().when(postService).updatePost(1L, postDTO);

        ResponseEntity<?> responseEntity = postController.updatePost(1L, postDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatePostNotFound() throws PostNotFoundException {
        PostDTO postDTO = new PostDTO(1L, "Updated Post Title", "Updated Post Content", 1L);

        doThrow(new PostNotFoundException("Post not found")).when(postService).updatePost(1L, postDTO);

        ResponseEntity<?> responseEntity = postController.updatePost(1L, postDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeletePost() throws PostNotFoundException {
        doNothing().when(postService).deletePost(1L);

        ResponseEntity<?> responseEntity = postController.deletePost(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeletePostNotFound() throws PostNotFoundException {
        doThrow(new PostNotFoundException("Post not found")).when(postService).deletePost(1L);

        ResponseEntity<?> responseEntity = postController.deletePost(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
