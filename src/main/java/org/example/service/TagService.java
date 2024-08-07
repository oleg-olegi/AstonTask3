package org.example.service;

import org.example.dto.TagDTO;
import org.example.entity.Tag;
import org.example.exceptions.TagNotFoundException;
import org.example.mapper.TagMapper;
import org.example.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public TagService(TagMapper tagMapper, TagRepository tagRepository) {
        this.tagMapper = tagMapper;
        this.tagRepository = tagRepository;
    }


    public Tag getTag(long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public void addTag(TagDTO tagDTO) {
        Tag tag = tagMapper.toEntity(tagDTO);
        tagRepository.save(tag);
    }

    public void updateTag(long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new TagNotFoundException("Tag not found")
        );
        if (tagDTO.getName() != null) {
            tag.setName(tagDTO.getName());
        }
        tagRepository.save(tag);
    }

    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }
}
