package org.example.repository;

import org.example.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Author} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for CRUD operations
 * and query execution on {@link Author} entities.
 * </p>
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

