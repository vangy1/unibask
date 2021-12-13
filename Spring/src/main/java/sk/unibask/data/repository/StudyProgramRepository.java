package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.unibask.data.model.StudyProgram;

import java.util.List;
import java.util.Optional;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Integer> {
    @Query("select sp from StudyProgram sp order by sp.longName asc")
    List<StudyProgram> findAllOrderByLongName();

    Optional<StudyProgram> findById(Long id);
}
