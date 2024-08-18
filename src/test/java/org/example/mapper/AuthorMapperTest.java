package org.example.mapper;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


public class AuthorMapperTest {

    private AuthorMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(AuthorMapper.class);
    }

    @Test
    void toAuthorDTO() {
        Author author = new Author();
        author.setName("Test name");
        author.setEmail("test@test.com");

        AuthorDTO authorDTO = mapper.toDTO(author);

        Assertions.assertEquals(author.getName(), authorDTO.getName());
        Assertions.assertEquals(author.getEmail(), authorDTO.getEmail());
    }

    @Test
    void toAuthorEntity() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Test name");
        authorDTO.setEmail("test@test.com");

        Author author = mapper.toEntity(authorDTO);

        Assertions.assertEquals(author.getName(), authorDTO.getName());
        Assertions.assertEquals(author.getEmail(), authorDTO.getEmail());
    }
}
