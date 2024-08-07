package org.example.mapper;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);

    Tag toEntity(TagDTO tagDTO);

}
