package org.example.service;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.exceptions.UserNotFoundException;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        logger.info("AuthorService");
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorDTO getUserById(Long id) {
        logger.info(" SERVICE - getUserById");
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", id)));
        logger.info(author.toString());
        return authorMapper.toDTO(author);
    }

    public List<AuthorDTO> getAllUsers() {
        List<AuthorDTO> authorDTOList = authorRepository.findAll().stream()
                .map(authorMapper::toDTO)
                .toList();
        if (authorDTOList.isEmpty()) {
            throw new UserNotFoundException("List is empty");
        }
        return authorDTOList;
    }

    public void createUser(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        if (author.getEmail() == null || author.getName() == null) {
            throw new IllegalArgumentException("Users fields cannot be empty");
        }
        authorRepository.save(author);
    }

    public void updateUser(long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("User with id '%s' not found", id))
        );
        if (authorDTO.getName() != null) {
            author.setName(authorDTO.getName());
        }
        if (authorDTO.getEmail() != null) {
            author.setEmail(authorDTO.getEmail());
        }
        authorRepository.save(author);
    }

    public void deleteUser(Long id) {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(String.format("User with id '%s' not found", id));
        }
    }
}
