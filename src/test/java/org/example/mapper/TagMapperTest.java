package org.example.mapper;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TagMapperTest {
    private TagMapper tagMapper;

    @BeforeEach
    public void setUp() {
        tagMapper = Mappers.getMapper(TagMapper.class);
    }

    @Test
    void toTagDTO() {
        Tag tag = new Tag();
        tag.setName("test");

        TagDTO tagDTO = tagMapper.toDTO(tag);

        Assertions.assertEquals(tagDTO.getName(), tag.getName());
    }

    @Test
    void toTagEntity(){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("test");

        Tag tag = tagMapper.toEntity(tagDTO);

        Assertions.assertEquals(tagDTO.getName(), tag.getName());
    }
}
