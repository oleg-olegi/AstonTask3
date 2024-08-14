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
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to Authors.
 */
@Service
@Transactional
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Constructor for AuthorService.
     *
     * @param authorRepository Repository for Author entities.
     * @param authorMapper     Mapper to convert between Author entities and DTOs.
     */
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        logger.info("AuthorService");
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * Retrieves an Author by their ID.
     *
     * @param id ID of the Author to be retrieved.
     * @return The AuthorDTO representing the Author.
     * @throws UserNotFoundException if the Author is not found.
     */
    public AuthorDTO getUserById(Long id) {
        logger.info("SERVICE - getUserById");
        Author author = authorRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", id)));
        return authorMapper.toDTO(author);
    }

    /**
     * Retrieves all Authors.
     *
     * @return List of AuthorDTOs representing all Authors.
     * @throws UserNotFoundException if the list of Authors is empty.
     */
    public List<AuthorDTO> getAllUsers() {
        List<AuthorDTO> authorDTOList = authorRepository.findAll().stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
        if (authorDTOList.isEmpty()) {
            throw new UserNotFoundException("List is empty");
        }
        return authorDTOList;
    }

    /**
     * Creates a new Author.
     *
     * @param authorDTO Data Transfer Object representing the Author to be created.
     * @throws IllegalArgumentException if any required fields in the AuthorDTO are null.
     */
    public void createUser(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);
        if (author.getEmail() == null || author.getName() == null) {
            throw new IllegalArgumentException("User fields cannot be empty");
        }
        authorRepository.save(author);
    }

    /**
     * Updates an existing Author.
     *
     * @param id        ID of the Author to be updated.
     * @param authorDTO Data Transfer Object containing updated Author data.
     * @throws UserNotFoundException if the Author with the specified ID is not found.
     */
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

    /**
     * Deletes an Author by their ID.
     *
     * @param id ID of the Author to be deleted.
     * @throws UserNotFoundException if the Author with the specified ID is not found.
     */
    public void deleteUser(Long id) {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(String.format("User with id '%s' not found", id));
        }
    }
}
