package sk.unibask.user.studyprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.unibask.data.repository.StudyProgramRepository;

import java.util.List;

@Service
public class StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;

    @Autowired
    public StudyProgramService(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    public List<StudyProgramDto> getAllStudyPrograms() {
        return studyProgramRepository.findAllOrderByLongName().stream().map(studyProgram -> new StudyProgramDto(studyProgram.getId(), studyProgram.getShortName(), studyProgram.getLongName())).toList();
    }
}
