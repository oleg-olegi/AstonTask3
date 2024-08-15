package org.example.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.dto.PostDTO;
import org.example.entity.Post;
import org.example.exceptions.PostNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetPostById() {
        Long postId = 1L;
        Post post = new Post();
        PostDTO postDTO = new PostDTO();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        PostDTO result = postService.getPostById(postId);

        assertNotNull(result);
        verify(postRepository).findById(postId);
        verify(postMapper).toDTO(post);
    }

    @Test
    void testGetPostById_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.getPostById(postId));
    }

    @Test
    void testGetAllPosts() {
        Post post = new Post();
        PostDTO postDTO = new PostDTO();
        when(postRepository.findAll()).thenReturn(List.of(post));
        when(postMapper.toDTO(post)).thenReturn(postDTO);

        List<PostDTO> result = postService.getAllPosts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(postRepository).findAll();
        verify(postMapper).toDTO(post);
    }

    @Test
    void testGetAllPosts_EmptyList() {
        when(postRepository.findAll()).thenReturn(List.of());

        assertThrows(PostNotFoundException.class, () -> postService.getAllPosts());
    }

    @Test
    void testCreatePost() {
        PostDTO postDTO = new PostDTO(1L, "title", "content", 1L);

        Post post = new Post();
        post.setTitle("title");
        post.setContent("content");

        when(postMapper.toEntity(postDTO)).thenReturn(post);

        postService.createPost(postDTO);

        verify(postMapper).toEntity(postDTO);
        verify(postRepository).save(post);
    }

    @Test
    void testCreatePostWithEmptyFields() {
        PostDTO postDTO = new PostDTO();
        Post post = new Post();
        when(postMapper.toEntity(postDTO)).thenReturn(post);
        assertThrows(IllegalArgumentException.class, () -> postService.createPost(postDTO));
    }


    @Test
    void testUpdatePost() {
        Long postId = 1L;
        PostDTO postDTO = new PostDTO(1L, "title", "content", 1L);
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        when(postMapper.toEntity(postDTO)).thenReturn(post);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.updatePost(postId, postDTO);
        assertNotNull(post);
        assertNotNull(post.getContent());
        assertNotNull(post.getTitle());
        verify(postRepository).findById(postId);
        verify(postRepository).save(post);
    }

    @Test
    void testUpdatePostWithNotNullFields() {
        Long postId = 1L;
        PostDTO postDTO = new PostDTO();
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.updatePost(postId, postDTO);

        verify(postRepository).findById(postId);
        verify(postRepository).save(post);
    }

    @Test
    void testUpdatePost_NotFound() {
        long postId = 1L;
        PostDTO postDTO = new PostDTO();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.updatePost(postId, postDTO));
    }

    @Test
    void testDeletePost() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));

        postService.deletePost(postId);

        verify(postRepository).findById(postId);
        verify(postRepository).deleteById(postId);
    }

    @Test
    void testDeletePost_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.deletePost(postId));
    }
}
