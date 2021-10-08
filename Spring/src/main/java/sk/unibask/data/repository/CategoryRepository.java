package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Long id);
}
