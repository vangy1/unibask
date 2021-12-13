package sk.unibask.entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.CommentRepository;
import sk.unibask.data.repository.EntryRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.entry.answer.AnswerDto;
import sk.unibask.entry.comment.CommentDto;
import sk.unibask.entry.question.QuestionDto;

import java.util.List;
import java.util.Map;

@Service
public class EntryService {
    private final AuthenticationService authenticationService;
    private final EntryRepository entryRepository;
    private final EntityToDtoService entityToDtoService;
    private final QuestionRepository questionRepository;
    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public EntryService(AuthenticationService authenticationService, EntryRepository entryRepository, EntityToDtoService entityToDtoService, QuestionRepository questionRepository, CommentRepository commentRepository, AnswerRepository answerRepository) {
        this.authenticationService = authenticationService;
        this.entryRepository = entryRepository;
        this.entityToDtoService = entityToDtoService;
        this.questionRepository = questionRepository;
        this.commentRepository = commentRepository;
        this.answerRepository = answerRepository;
    }

    public Map<String, List<? extends EntryDto>> getAllEntriesOfAccount(Long userId) {
        List<QuestionDto> questionDtos = questionRepository.findAllByAccountWithVotes(userId).stream().map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
        List<AnswerDto> answerDtos = answerRepository.findAllByAccountWithVotes(userId).stream().map(answer -> entityToDtoService.answerToAnswerDto(answer, null)).toList();
        List<CommentDto> commentDtos = commentRepository.findAllByAccountWithVotes(userId).stream().map(comment -> entityToDtoService.commentToCommentDto(comment, null)).toList();

        return Map.of("questions", questionDtos, "answers", answerDtos, "comments", commentDtos);


//        List<Question> questions = questionRepository.findAllByAccount(userId);
//        for (Question question : questions) {
//            System.out.println(question.getVotes());
//        }
//        System.out.println(questions.size() + "a");

    }
}
