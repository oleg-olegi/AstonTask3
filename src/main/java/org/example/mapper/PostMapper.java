package org.example.mapper;

import org.example.dto.PostDTO;
import org.example.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    PostDTO toDTO(Post post);

    @Mapping(source = "authorId", target = "author.id")
    Post toEntity(PostDTO postDTO);
}

