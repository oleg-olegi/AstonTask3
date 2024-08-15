package org.example.repository;

import org.example.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DBConfigurationClass.class)
@ComponentScan(basePackages = "org.example.repository")
 class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void cleanUp() {
        tagRepository.deleteAll();
    }

    @Test
    void testSaveAndFind() {

        Tag tag = new Tag();
        tag.setName("test tag");
        tagRepository.save(tag);

        Tag foundTag = tagRepository.findById(tag.getId()).orElse(null);

        assertThat(foundTag).isNotNull();

        assert foundTag != null;
        assertThat(foundTag.getName()).isEqualTo("test tag");
    }

    @Test
    void getByIdTest() {
        Tag tag1 = new Tag();
        tag1.setName("test tag1");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("test tag2");
        tagRepository.save(tag2);

        Long id1, id2;
        id1 = tag1.getId();
        id2 = tag2.getId();

        Tag foundTag1 = tagRepository.findById(id1).orElse(null);
        Tag foundTag2 = tagRepository.findById(id2).orElse(null);

        assertThat(foundTag1).isNotNull();
        assertThat(foundTag2).isNotNull();

        assert foundTag1 != null;
        assert foundTag2 != null;

        assertThat("test tag1").isEqualTo(foundTag1.getName());
        assertThat("test tag2").isEqualTo(foundTag2.getName());
    }

    @Test
    void getAllTest() {
        Tag tag1 = new Tag();
        tag1.setName("test tag1");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("test tag2");
        tagRepository.save(tag2);

        List<Tag> tags = tagRepository.findAll();

        Assertions.assertFalse(tags.isEmpty());
        assertEquals(2, tags.size());
    }

    @Test
    void deleteByIdTest() {
        Tag tag1 = new Tag();
        tag1.setName("test tag1");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("test tag2");
        tagRepository.save(tag2);

        List<Tag> tags = tagRepository.findAll();
        Assertions.assertFalse(tags.isEmpty());
        assertEquals(2, tags.size());

        Long id = tag1.getId();

        tagRepository.deleteById(id);
        Tag tagAfterDelete = tagRepository.findById(id).orElse(null);
        Assertions.assertNull(tagAfterDelete);

        List<Tag> tagsAfterDelete = tagRepository.findAll();
        assertEquals(1, tagsAfterDelete.size());
    }

    @Test
    void updateTest() {
        Tag tag1 = new Tag();
        tag1.setName("test tag1");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("test tag2");
        tagRepository.save(tag2);

        Long id = tag1.getId();

        Tag foundedTag = tagRepository.findById(id).orElse(null);
        assert foundedTag != null;
        foundedTag.setName("new test tag name");
        tagRepository.save(foundedTag);

        assert tagRepository.findById(id).isPresent();
        assertEquals("new test tag name", tagRepository.findById(id).get().getName());

        assertEquals(id, foundedTag.getId());
    }
}
