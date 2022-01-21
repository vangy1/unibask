package sk.unibask.profile.studyprogram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.repository.StudyProgramRepository;

import java.util.List;

@Service
public class StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public StudyProgramService(StudyProgramRepository studyProgramRepository, AuthenticationService authenticationService) {
        this.studyProgramRepository = studyProgramRepository;
        this.authenticationService = authenticationService;
    }

    public List<StudyProgramDto> getAllStudyPrograms() {
        authenticationService.getLoggedAccount();
        return studyProgramRepository.findAllOrderByLongName().stream().map(studyProgram -> new StudyProgramDto(studyProgram.getId(), studyProgram.getShortName(), studyProgram.getLongName())).toList();
    }
}
