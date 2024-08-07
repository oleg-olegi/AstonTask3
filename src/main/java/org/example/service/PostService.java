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

@Service
@Transactional
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(org.example.service.PostService.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostDTO getPostById(Long id) {
        logger.info(" SERVICE - getPostById");
        Post post = postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException(String.format("Post with id %s not found", id)));
        logger.info(post.toString());
        return postMapper.toDTO(post);
    }

    public List<PostDTO> getAllPosts() {
        List<PostDTO> postDTOList = postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .toList();
        if (postDTOList.isEmpty()) {
            throw new PostNotFoundException("List is empty");
        }
        return postDTOList;
    }

    public void createPost(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        logger.info("CREATE_POST {}", post.toString());
        if (post.getContent() == null || post.getTitle() == null) {
            throw new IllegalArgumentException("Posts fields cannot be empty");
        }
        postRepository.save(post);

    }

    public void updatePost(long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(
                String.format("post with id '%s' not found", id))
        );
        if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }
        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException(String.format("Post with id '%s' not found", id));
        }
    }
}
