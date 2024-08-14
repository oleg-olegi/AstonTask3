package org.example.repository;

import org.example.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Post} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for CRUD operations
 * and query execution on {@link Post} entities.
 * </p>
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
