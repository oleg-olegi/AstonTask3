package org.example.mapper;

import org.example.dto.PostDTO;

import org.example.entity.Post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class PostMapperTest {

    private PostMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PostMapper.class);
    }

    @Test
    void toPostDTO() {
        Post post = new Post();
        post.setTitle("Test title");
        post.setContent("test content");

        PostDTO postDTO = mapper.toDTO(post);

        Assertions.assertEquals(post.getTitle(), postDTO.getTitle());
        Assertions.assertEquals(post.getContent(), postDTO.getContent());
    }

    @Test
    void toPostEntity() {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Test title");
        postDTO.setContent("test content");

        Post post = mapper.toEntity(postDTO);

        Assertions.assertEquals(post.getTitle(), postDTO.getTitle());
        Assertions.assertEquals(post.getContent(), postDTO.getContent());
    }
}