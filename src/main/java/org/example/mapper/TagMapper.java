package org.example.mapper;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Tag} entities and {@link TagDTO} objects.
 * <p>
 * This interface uses MapStruct to automatically generate the implementation for mapping
 * between the entity and DTO. The {@code componentModel = "spring"} annotation allows
 * this mapper to be injected as a Spring bean.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    /**
     * Converts a {@link Tag} entity to a {@link TagDTO}.
     *
     * @param tag the Tag entity to convert.
     * @return the corresponding TagDTO.
     */
    TagDTO toDTO(Tag tag);

    /**
     * Converts a {@link TagDTO} to a {@link Tag} entity.
     *
     * @param tagDTO the TagDTO to convert.
     * @return the corresponding Tag entity.
     */
    Tag toEntity(TagDTO tagDTO);
}
