package org.example.service;

import org.example.dto.PostDTO;
import org.example.entity.Post;
import org.example.exceptions.PostNotFoundException;
import org.example.mapper.PostMapper;
import org.example.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to Posts.
 *
 * @author oleg shinkevich
 */
@Service
@Transactional
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    /**
     * Constructor for PostService.
     *
     * @param postRepository Repository for Post entities.
     * @param postMapper     Mapper to convert between Post entities and DTOs.
     */
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Retrieves a Post by its ID.
     *
     * @param id ID of the Post to be retrieved.
     * @return The PostDTO representing the Post.
     * @throws PostNotFoundException if the Post is not found.
     */
    public PostDTO getPostById(Long id) {
        logger.info(" SERVICE - getPostById");
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException(String.format("Post with id %s not found", id)));
        return postMapper.toDTO(post);
    }

    /**
     * Retrieves all Posts.
     *
     * @return List of PostDTOs representing all Posts.
     * @throws PostNotFoundException if the list of Posts is empty.
     */
    public List<PostDTO> getAllPosts() {
        List<PostDTO> postDTOList = postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
        if (postDTOList.isEmpty()) {
            throw new PostNotFoundException("List is empty");
        }
        return postDTOList;
    }

    /**
     * Creates a new Post.
     *
     * @param postDTO Data Transfer Object representing the Post to be created.
     * @throws IllegalArgumentException if any required fields in the PostDTO are null.
     */
    public void createPost(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        logger.info("CREATE_POST {}", post);
        if (post.getContent() == null || post.getTitle() == null) {
            throw new IllegalArgumentException("Post fields cannot be empty");
        }
        postRepository.save(post);
    }

    /**
     * Updates an existing Post.
     *
     * @param id      ID of the Post to be updated.
     * @param postDTO Data Transfer Object containing updated Post data.
     * @throws PostNotFoundException if the Post with the specified ID is not found.
     */
    public void updatePost(long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(
                String.format("Post with id '%s' not found", id))
        );
        if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }
        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        postRepository.save(post);
    }

    /**
     * Deletes a Post by its ID.
     *
     * @param id ID of the Post to be deleted.
     * @throws PostNotFoundException if the Post with the specified ID is not found.
     */
    public void deletePost(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException(String.format("Post with id '%s' not found", id));
        }
    }
}
