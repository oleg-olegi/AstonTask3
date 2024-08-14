package org.example.service;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.example.exceptions.TagNotFoundException;
import org.example.mapper.TagMapper;
import org.example.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to Tags.
 *
 * @author oleg shinkevich
 */
@Service
@Transactional
public class TagService {
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    /**
     * Constructor for TagService.
     *
     * @param tagMapper     Mapper to convert between Tag entities and DTOs.
     * @param tagRepository Repository for Tag entities.
     */
    public TagService(TagMapper tagMapper, TagRepository tagRepository) {
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
    }

    /**
     * Retrieves a Tag by its ID.
     *
     * @param id ID of the Tag to be retrieved.
     * @return The Tag entity.
     * @throws RuntimeException if the Tag is not found.
     */
    public Tag getTag(long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    /**
     * Retrieves all Tags.
     *
     * @return List of TagDTOs representing all Tags.
     */
    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new Tag.
     *
     * @param tagDTO Data Transfer Object representing the Tag to be added.
     */
    public void addTag(TagDTO tagDTO) {
        Tag tag = tagMapper.toEntity(tagDTO);
        tagRepository.save(tag);
    }

    /**
     * Updates an existing Tag.
     *
     * @param id     ID of the Tag to be updated.
     * @param tagDTO Data Transfer Object containing updated Tag data.
     * @throws TagNotFoundException if the Tag with the specified ID is not found.
     */
    public void updateTag(long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new TagNotFoundException("Tag not found")
        );
        if (tagDTO.getName() != null) {
            tag.setName(tagDTO.getName());
        }
        tagRepository.save(tag);
    }

    /**
     * Deletes a Tag by its ID.
     *
     * @param id ID of the Tag to be deleted.
     */
    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }
}
