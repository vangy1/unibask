package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
