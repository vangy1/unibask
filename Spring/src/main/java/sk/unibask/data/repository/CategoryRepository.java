package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Category;
import sk.unibask.data.model.Entry;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Long id);

    @Query("SELECT c FROM Category c where c not in (select c from Category c join c.parentCategories pc)")
    List<Category> findRoots();


    //    @Query(
//            value = "SELECT * FROM category_parent_categories u WHERE u.status = 1",
//            nativeQuery = true)
    @Query("SELECT c FROM Category c where c not in (select c from Category c join c.childrenCategories pc)")
    List<Category> findLeafs();

    @Query("SELECT e FROM Category cat JOIN cat.questions q LEFT JOIN q.answers a LEFT JOIN Comment c on c.id = a.id or c.id = q.id JOIN Entry e on e.id = q.id or e.id = a.id or e.id = c.id")
    List<Entry> findAllEntries(@Param("category") Category category);
}
