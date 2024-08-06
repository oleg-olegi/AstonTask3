package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.exceptions.UserNotFoundException;
import org.example.mapper.UserMapper;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        System.out.println("Service created");
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserById(Long id) {
        logger.info(" SERVICE - getUserById");
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("User with id %s not found", id)));
        logger.info(user.toString());
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
        if (userDTOList.isEmpty()) {
            throw new UserNotFoundException("List is empty");
        }
        return userDTOList;
    }

    public void createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if (user.getId() == null || user.getName() == null) {
            throw new IllegalArgumentException("Users fields cannot be empty");
        }
        userRepository.save(user);
    }

    public void updateUser(long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("User with id '%s' not found", id))
        );
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(String.format("User with id '%s' not found", id));
        }
    }
}
