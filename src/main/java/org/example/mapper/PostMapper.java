package org.example.mapper;
import org.example.dto.PostDTO;
import org.example.entity.Author;
import org.example.entity.Post;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "author.id", target = "authorId")
    PostDTO toDTO(Post post);
    @Mapping(source = "authorId", target = "author.id")
    Post toEntity(PostDTO postDTO);

//    @AfterMapping
//    default void setAuthor(PostDTO postDTO, @MappingTarget Post post) {
//        if (postDTO.getAuthorId() != null) {
//            Author author = new Author();
//            author.setId(postDTO.getAuthorId());
//            post.setAuthor(author);
//        }
//    }
}

