package org.example.repository;

import org.example.entity.Author;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(DBConfigurationClass.class)
@ComponentScan(basePackages = "org.example.repository")
 class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {
        authorRepository.deleteAll();
    }

    @Test
    void testSaveAndFind() {

        Author author = new Author();
        author.setName("John Connor");
        author.setEmail("skyNet@gmail.com");
        authorRepository.save(author);

        Author foundAuthor = authorRepository.findById(author.getId()).orElse(null);

        assertThat(foundAuthor).isNotNull();

        assert foundAuthor != null;
        assertThat(foundAuthor.getName()).isEqualTo("John Connor");
    }

    @Test
    void getByIdTest() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Connor");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Sarah Connor");
        authorRepository.save(author2);

        Author foundAuthor1 = authorRepository.findById(1L).orElse(null);
        Author foundAuthor2 = authorRepository.findById(2L).orElse(null);
        Author foundAuthor3 = authorRepository.findById(3L).orElse(null);
        assertThat(foundAuthor1).isNotNull();
        assertThat(foundAuthor2).isNotNull();
        assertThat(foundAuthor3).isNull();
        assert foundAuthor1 != null;
        assert foundAuthor2 != null;
        assert foundAuthor3 == null;
        assertThat("John Connor").isEqualTo(foundAuthor1.getName());
        assertThat("Sarah Connor").isEqualTo(foundAuthor2.getName());
    }

    @Test
    @Transactional
    void getAllTest() {

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John Connor");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Sarah Connor");
        authorRepository.save(author2);

        List<Author> authors = authorRepository.findAll();

        assertFalse(authors.isEmpty());
        assertEquals(2, authors.size());
    }

    @Test
    void deleteByIdTest() {

        Author author1 = new Author();
        author1.setName("John Connor");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Sarah Connor");
        authorRepository.save(author2);

        List<Author> authors = authorRepository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(2, authors.size());

        Long id = author1.getId();

        authorRepository.deleteById(id);
        Author authorAfterDelete = authorRepository.findById(id).orElse(null);
        assertNull(authorAfterDelete);

        List<Author> authorsAfterDelete = authorRepository.findAll();
        assertEquals(1, authorsAfterDelete.size());
    }

    @Test
    void updateTest() {
        Author author1 = new Author();
        author1.setName("John Connor");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Sarah Connor");
        authorRepository.save(author2);

        Long id = author1.getId();

        Author foundedAuthor = authorRepository.findById(id).orElse(null);
        assert foundedAuthor != null;
        foundedAuthor.setName("Sam Connor");
        authorRepository.save(foundedAuthor);

        assert authorRepository.findById(id).isPresent();
        assertEquals("Sam Connor", authorRepository.findById(id).get().getName());
    }
}
