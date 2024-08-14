package org.example.mapper;

import org.example.dto.PostDTO;
import org.example.entity.Post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link Post} entities and {@link PostDTO} objects.
 * <p>
 * This interface uses MapStruct to automatically generate the implementation for mapping
 * between the entity and DTO. The {@code componentModel = "spring"} annotation allows
 * this mapper to be injected as a Spring bean. The {@code @Mapping} annotations specify
 * custom mappings between fields of the {@link Post} and {@link PostDTO}.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface PostMapper {
    /**
     * Converts a {@link Post} entity to a {@link PostDTO}.
     * <p>
     * The {@code author.id} field in the {@link Post} entity is mapped to the {@code authorId}
     * field in the {@link PostDTO}.
     * </p>
     *
     * @param post the Post entity to convert.
     * @return the corresponding PostDTO.
     */
    @Mapping(source = "author.id", target = "authorId")
    PostDTO toDTO(Post post);

    /**
     * Converts a {@link PostDTO} to a {@link Post} entity.
     * <p>
     * The {@code authorId} field in the {@link PostDTO} is mapped to the {@code author.id}
     * field in the {@link Post} entity.
     * </p>
     *
     * @param postDTO the PostDTO to convert.
     * @return the corresponding Post entity.
     */
    @Mapping(source = "authorId", target = "author.id")
    Post toEntity(PostDTO postDTO);
}

