package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Category;
import sk.unibask.data.model.Entry;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM categories c where c not in (select c from categories c join c.parentCategories pc)")
    List<Category> findRoots();

    @Query("SELECT c FROM categories c where c not in (select c from categories c join c.childrenCategories pc)")
    List<Category> findLeafs();

    @Query("SELECT e FROM categories cat JOIN cat.questions q LEFT JOIN q.answers a LEFT JOIN comments c on c.id = a.id or c.id = q.id JOIN entries e on e.id = q.id or e.id = a.id or e.id = c.id")
    List<Entry> findAllEntries(@Param("category") Category category);
}
