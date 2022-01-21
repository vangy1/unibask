package sk.unibask.profile.studyprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/study-programs")
public class StudyProgramController {

    private final StudyProgramService studyProgramService;

    @Autowired
    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    @GetMapping
    public List<StudyProgramDto> getAllStudyPrograms() {
        return studyProgramService.getAllStudyPrograms();
    }
}
