package org.example.mapper;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Author} entities and {@link AuthorDTO} objects.
 * <p>
 * This interface uses MapStruct to automatically generate the implementation for mapping
 * between the entity and DTO. The {@code componentModel = "spring"} annotation allows
 * this mapper to be injected as a Spring bean.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    /**
     * Converts an {@link Author} entity to an {@link AuthorDTO}.
     *
     * @param author the Author entity to convert.
     * @return the corresponding AuthorDTO.
     */
    AuthorDTO toDTO(Author author);

    /**
     * Converts an {@link AuthorDTO} to an {@link Author} entity.
     *
     * @param authorDTO the AuthorDTO to convert.
     * @return the corresponding Author entity.
     */
    Author toEntity(AuthorDTO authorDTO);
}