package org.example.controller;

import org.example.dto.AuthorDTO;
import org.example.exceptions.UserNotFoundException;
import org.example.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Author Name", "Author Email");

        when(authorService.getUserById(1L)).thenReturn(authorDTO);

        ResponseEntity<?> responseEntity = authorController.getUserById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authorDTO, responseEntity.getBody());
    }

    @Test
    void testGetUserByIdNotFound() throws UserNotFoundException {
        when(authorService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));

        ResponseEntity<?> responseEntity = authorController.getUserById(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllUsers() throws UserNotFoundException {
        List<AuthorDTO> authors = new ArrayList<>();
        authors.add(new AuthorDTO(1L, "Author 1", "email1"));
        authors.add(new AuthorDTO(2L, "Author 2", "email2"));

        when(authorService.getAllUsers()).thenReturn(authors);

        ResponseEntity<?> responseEntity = authorController.getAllUsers();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(authors, responseEntity.getBody());
    }

    @Test
    void testGetAllUsersNotFound() throws UserNotFoundException {
        when(authorService.getAllUsers()).thenThrow(new UserNotFoundException("Users not found"));

        ResponseEntity<?> responseEntity = authorController.getAllUsers();
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreateUser() throws UserNotFoundException {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Author Name", "email");

        doNothing().when(authorService).createUser(authorDTO);

        ResponseEntity<?> responseEntity = authorController.createUser(authorDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testCreateUserBadRequest() throws UserNotFoundException {
        AuthorDTO authorDTO = new AuthorDTO(1L, null, null);

        doThrow(new IllegalArgumentException("User fields cannot be empty")).when(authorService).createUser(authorDTO);

        ResponseEntity<?> responseEntity = authorController.createUser(authorDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateUser() throws UserNotFoundException {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Updated Author Name","Updated email");

        doNothing().when(authorService).updateUser(1L, authorDTO);

        ResponseEntity<?> responseEntity = authorController.updateUser(1L, authorDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateUserNotFound() throws UserNotFoundException {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Updated Author Name", "Updated email");

        doThrow(new UserNotFoundException("User not found")).when(authorService).updateUser(1L, authorDTO);

        ResponseEntity<?> responseEntity = authorController.updateUser(1L, authorDTO);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteUser() throws UserNotFoundException {
        doNothing().when(authorService).deleteUser(1L);

        ResponseEntity<?> responseEntity = authorController.deleteUser(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() throws UserNotFoundException {
        doThrow(new UserNotFoundException("User not found")).when(authorService).deleteUser(1L);

        ResponseEntity<?> responseEntity = authorController.deleteUser(1L);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
