package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sk.unibask.data.model.StudyProgram;

import java.util.List;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    @Query("select sp from study_programs sp order by sp.longName asc")
    List<StudyProgram> findAllOrderByLongName();
}
