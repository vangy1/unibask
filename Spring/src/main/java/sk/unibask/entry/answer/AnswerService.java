package sk.unibask.entry.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Answer;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.QuestionRepository;

import java.util.Date;

@Service
public class AnswerService {
    private final AuthenticationService authenticationService;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AuthenticationService authenticationService, AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.authenticationService = authenticationService;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Answer createNewAnswer(String text, boolean isAnonymous, Long questionId) {
        var question = questionRepository.findById(questionId).get();

        var answer = new Answer();
        answer.setEntryText(text);
        answer.setEntryText(text);
        answer.setCreationDate(new Date());
        answer.setAnonymous(isAnonymous);
        answer.setAccount(authenticationService.getLoggedAccount());
        answer.setQuestion(question);
        answerRepository.save(answer);

        question.setLastActivity(new Date());
        questionRepository.save(question);

        return answer;
    }

}
