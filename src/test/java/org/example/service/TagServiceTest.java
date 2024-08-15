package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.example.exceptions.TagNotFoundException;
import org.example.mapper.TagMapper;
import org.example.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTag() {
        long tagId = 1L;
        Tag tag = new Tag("TestName");
        TagDTO expectedTagDTO = new TagDTO(1L, "TestName");

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));
        when(tagMapper.toDTO(tag)).thenReturn(expectedTagDTO);

        TagDTO result = tagService.getTag(tagId);

        assertNotNull(result);
        assertEquals(expectedTagDTO, result);
        verify(tagRepository).findById(tagId);
        verify(tagMapper).toDTO(tag);
    }
    @Test
    void testGetTag_NotFound() {
        long tagId = 1L;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tagService.getTag(tagId));
    }

    @Test
    void testGetAllTags() {
        Tag tag = new Tag();
        TagDTO tagDTO = new TagDTO();
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagMapper.toDTO(tag)).thenReturn(tagDTO);

        List<TagDTO> result = tagService.getAllTags();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(tagRepository).findAll();
        verify(tagMapper).toDTO(tag);
    }

    @Test
    void testAddTag() {
        TagDTO tagDTO = new TagDTO();
        Tag tag = new Tag();
        when(tagMapper.toEntity(tagDTO)).thenReturn(tag);

        tagService.addTag(tagDTO);

        verify(tagMapper).toEntity(tagDTO);
        verify(tagRepository).save(tag);
    }

    @Test
    void testUpdateTag() {
        long tagId = 1L;
        TagDTO tagDTO = new TagDTO();
        Tag tag = new Tag();
        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        tagService.updateTag(tagId, tagDTO);

        verify(tagRepository).findById(tagId);
        verify(tagRepository).save(tag);
    }

    @Test
    void testUpdateTag_NotFound() {
        long tagId = 1L;
        TagDTO tagDTO = new TagDTO();
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        assertThrows(TagNotFoundException.class, () -> tagService.updateTag(tagId, tagDTO));
    }

    @Test
    void testDeleteTag() {
        long tagId = 1L;

        tagService.deleteTag(tagId);

        verify(tagRepository).deleteById(tagId);
    }
}
