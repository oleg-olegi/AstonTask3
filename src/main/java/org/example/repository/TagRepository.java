package org.example.repository;

import org.example.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Tag} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for CRUD operations
 * and query execution on {@link Tag} entities.
 * </p>
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
