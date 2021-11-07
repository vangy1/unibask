package sk.unibask.entry.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.authentication.UserService;
import sk.unibask.data.model.Answer;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.entry.comment.CommentService;

import java.util.Date;

@Service
public class AnswerService {
    private final AuthenticationService authenticationService;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public AnswerService(AuthenticationService authenticationService, AnswerRepository answerRepository, QuestionRepository questionRepository, UserService userService, CommentService commentService) {
        this.authenticationService = authenticationService;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Transactional
    public Answer createNewAnswer(String text, boolean isAnonymous, Long questionId) {
        var answer = new Answer();
        answer.setEntryText(text);
        answer.setEntryText(text);
        answer.setCreationDate(new Date());
        answer.setAnonymous(isAnonymous);
        answer.setAccount(authenticationService.getLoggedAccount());
        answer.setQuestion(questionRepository.findById(questionId).get());

        answerRepository.save(answer);
        return answer;
    }

    @Transactional
    public AnswerDto answerToDto(Answer answer) {
        return new AnswerDto(answer.getId(), answer.getEntryText(), 0L, answer.getCreationDate(), userService.getUser(answer.getAccount()), commentService.findCommentsByEntryId(answer.getId()), false);
    }
}
