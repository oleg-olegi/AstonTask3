package org.example.mapper;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toDTO(Author author);

    Author toEntity(AuthorDTO authorDTO);
}