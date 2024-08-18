package org.example.service;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.exceptions.UserNotFoundException;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        Long userId = 1L;
        Author author = new Author();
        author.setId(userId);
        author.setName("Author Name");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(userId);
        authorDTO.setName("Author Name");

        when(authorRepository.findById(userId)).thenReturn(Optional.of(author));
        when(authorMapper.toDTO(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Author Name", result.getName());
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;
        when(authorRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authorService.getUserById(userId));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers_WhenUsersExist() {
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author One");
        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Author Two");

        AuthorDTO authorDTO1 = new AuthorDTO();
        authorDTO1.setId(1L);
        authorDTO1.setName("Author One");
        AuthorDTO authorDTO2 = new AuthorDTO();
        authorDTO2.setId(2L);
        authorDTO2.setName("Author Two");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        when(authorMapper.toDTO(author1)).thenReturn(authorDTO1);
        when(authorMapper.toDTO(author2)).thenReturn(authorDTO2);

        List<AuthorDTO> result = authorService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllUsers_ShouldThrowException_WhenNoUsersExist() {
        when(authorRepository.findAll()).thenReturn(List.of());

        assertThrows(UserNotFoundException.class, () -> authorService.getAllUsers());
    }

    @Test
    void createUser_ShouldSaveUser_WhenUserIsValid() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Author");
        authorDTO.setEmail("author@example.com");

        Author author = new Author();
        author.setName("New Author");
        author.setEmail("author@example.com");

        when(authorMapper.toEntity(authorDTO)).thenReturn(author);

        authorService.createUser(authorDTO);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void createUser_ShouldThrowException_WhenUserFieldsAreEmpty() {
        AuthorDTO authorDTO = new AuthorDTO();
        Author author = new Author();

        when(authorMapper.toEntity(authorDTO)).thenReturn(author);
        assertThrows(IllegalArgumentException.class, () -> authorService.createUser(authorDTO));
    }

    @Test
    void testUpdateUser_AllFieldsUpdated() {
        long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Name");
        authorDTO.setEmail("new.email@example.com");

        Author author = new Author();
        author.setId(authorId);
        author.setName("Old Name");
        author.setEmail("old.email@example.com");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        authorService.updateUser(authorId, authorDTO);

        assertEquals("New Name", author.getName());
        assertEquals("new.email@example.com", author.getEmail());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateUser_NameUpdatedOnly() {
        long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Name");
        authorDTO.setEmail(null);

        Author author = new Author();
        author.setId(authorId);
        author.setName("Old Name");
        author.setEmail("old.email@example.com");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        authorService.updateUser(authorId, authorDTO);

        assertEquals("New Name", author.getName());
        assertEquals("old.email@example.com", author.getEmail());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateUser_EmailUpdatedOnly() {
        long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(null);
        authorDTO.setEmail("new.email@example.com");

        Author author = new Author();
        author.setId(authorId);
        author.setName("Old Name");
        author.setEmail("old.email@example.com");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        authorService.updateUser(authorId, authorDTO);

        assertEquals("Old Name", author.getName());
        assertEquals("new.email@example.com", author.getEmail());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateUser_NoFieldsUpdated() {
        long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(null);
        authorDTO.setEmail(null);

        Author author = new Author();
        author.setId(authorId);
        author.setName("Old Name");
        author.setEmail("old.email@example.com");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        authorService.updateUser(authorId, authorDTO);

        assertEquals("Old Name", author.getName());
        assertEquals("old.email@example.com", author.getEmail());
        verify(authorRepository).findById(authorId);
        verify(authorRepository).save(author);
    }

    @Test
    void testUpdateUser_NotFound() {
        long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Name");
        authorDTO.setEmail("new.email@example.com");

        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authorService.updateUser(authorId, authorDTO));
        verify(authorRepository).findById(authorId);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        Long userId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Name");
        authorDTO.setEmail("updated@example.com");

        Author author = new Author();
        author.setId(userId);
        author.setName("Original Name");
        author.setEmail("original@example.com");

        when(authorRepository.findById(userId)).thenReturn(Optional.of(author));

        authorService.updateUser(userId, authorDTO);

        assertEquals("Updated Name", author.getName());
        assertEquals("updated@example.com", author.getEmail());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        long userId = 1L;
        AuthorDTO authorDTO = new AuthorDTO();

        when(authorRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authorService.updateUser(userId, authorDTO));
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        Long userId = 1L;
        Author author = new Author();
        author.setId(userId);

        when(authorRepository.findById(userId)).thenReturn(Optional.of(author));

        authorService.deleteUser(userId);

        verify(authorRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;
        when(authorRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authorService.deleteUser(userId));
    }

    @Test
    void testCreateUser_NullEmail() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("John Doe");
        authorDTO.setEmail(null);

        Author author = new Author();
        author.setName("John Doe");
        author.setEmail(null);

        when(authorMapper.toEntity(authorDTO)).thenReturn(author);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authorService.createUser(authorDTO));

        assertEquals("User fields cannot be empty", exception.getMessage());
        verify(authorMapper).toEntity(authorDTO);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testCreateUser_NullName() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(null);
        authorDTO.setEmail("john.doe@example.com");

        Author author = new Author();
        author.setName(null);
        author.setEmail("john.doe@example.com");

        when(authorMapper.toEntity(authorDTO)).thenReturn(author);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authorService.createUser(authorDTO));

        assertEquals("User fields cannot be empty", exception.getMessage());
        verify(authorMapper).toEntity(authorDTO);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testCreateUser_NullNameAndEmail() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(null);
        authorDTO.setEmail(null);

        Author author = new Author();
        author.setName(null);
        author.setEmail(null);

        when(authorMapper.toEntity(authorDTO)).thenReturn(author);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authorService.createUser(authorDTO));

        assertEquals("User fields cannot be empty", exception.getMessage());
        verify(authorMapper).toEntity(authorDTO);
        verify(authorRepository, never()).save(any(Author.class));
    }
}
