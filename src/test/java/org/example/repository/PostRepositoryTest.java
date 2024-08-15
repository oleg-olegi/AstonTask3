package org.example.repository;

import org.example.entity.Post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(DBConfigurationClass.class)
@ComponentScan(basePackages = "org.example.repository")
 class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    void testSaveAndFind() {

        Post post = new Post();
        post.setTitle("test title");
        post.setContent("test content");
        postRepository.save(post);

        Post foundPost = postRepository.findById(post.getId()).orElse(null);

        assertThat(foundPost).isNotNull();

        assert foundPost != null;
        assertThat(foundPost.getContent()).isEqualTo("test content");
        assertThat(foundPost.getTitle()).isEqualTo("test title");
    }

    @Test
    void getByIdTest() {
        Post post1 = new Post();
        post1.setTitle("test title1");
        post1.setContent("test content1");
        postRepository.save(post1);


        Post post2 = new Post();
        post2.setTitle("test title2");
        post2.setContent("test content2");
        postRepository.save(post2);

        Long id1, id2;
        id1 = post1.getId();
        id2 = post2.getId();


        Post foundPost1 = postRepository.findById(id1).orElse(null);
        Post foundPost2 = postRepository.findById(id2).orElse(null);


        assertThat(foundPost1).isNotNull();
        assertThat(foundPost2).isNotNull();

        assert foundPost1 != null;
        assert foundPost2 != null;

        assertThat("test title1").isEqualTo(foundPost1.getTitle());
        assertThat("test title2").isEqualTo(foundPost2.getTitle());
    }

    @Test
    void getAllTest() {
        Post post1 = new Post();
        post1.setTitle("test title1");
        post1.setContent("test content1");
        postRepository.save(post1);


        Post post2 = new Post();
        post2.setTitle("test title2");
        post2.setContent("test content2");
        postRepository.save(post2);

        List<Post> posts = postRepository.findAll();

        Assertions.assertFalse(posts.isEmpty());
        assertEquals(2, posts.size());
    }

    @Test
    void deleteByIdTest() {
        Post post1 = new Post();
        post1.setTitle("test title1");
        post1.setContent("test content1");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("test title2");
        post2.setContent("test content2");
        postRepository.save(post2);

        List<Post> posts = postRepository.findAll();
        Assertions.assertFalse(posts.isEmpty());
        assertEquals(2, posts.size());

        Long id = post1.getId();

        postRepository.deleteById(id);
        Post postAfterDelete = postRepository.findById(id).orElse(null);
        Assertions.assertNull(postAfterDelete);

        List<Post> postsAfterDelete = postRepository.findAll();
        assertEquals(1, postsAfterDelete.size());
    }

    @Test
    void updateTest() {
        Post post1 = new Post();
        post1.setTitle("test title1");
        post1.setContent("test content1");
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("test title2");
        post2.setContent("test content2");
        postRepository.save(post2);

        Long id = post1.getId();

        Post foundedpost = postRepository.findById(id).orElse(null);
        assert foundedpost != null;
        foundedpost.setTitle("new test title");
        postRepository.save(foundedpost);

        assert postRepository.findById(id).isPresent();
        assertEquals("new test title", postRepository.findById(id).get().getTitle());
    }
}
