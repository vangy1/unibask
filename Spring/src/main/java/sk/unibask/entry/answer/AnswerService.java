package sk.unibask.entry.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Answer;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.notification.NotificationService;

import java.util.Date;
import java.util.Objects;

@Service
public class AnswerService {
    private final AuthenticationService authenticationService;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final NotificationService notificationService;

    @Autowired
    public AnswerService(AuthenticationService authenticationService, AnswerRepository answerRepository, QuestionRepository questionRepository, NotificationService notificationService) {
        this.authenticationService = authenticationService;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Answer createNewAnswer(String text, String unformattedText, boolean isAnonymous, Long questionId) {
        var loggedAccount = authenticationService.getLoggedAccount();
        if (text == null || text.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Odpoveď musí mať text.");
        var question = questionRepository.findById(questionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Otázka odpovede neexistuje."));
        if (question == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Otázka odpovede neexistuje.");

        var answer = new Answer();
        answer.setEntryText(text);
        answer.setEntryTextUnformatted(unformattedText);
        answer.setCreationDate(new Date());
        answer.setAnonymous(isAnonymous);
        answer.setAccount(loggedAccount);
        answer.setQuestion(question);
        answerRepository.save(answer);

        question.setLastActivity(new Date());
        questionRepository.save(question);

        notificationService.createNewAnswerNotification(answer);
        return answer;
    }

    @Transactional
    public void markSolved(Long answerId, boolean isSolved) {
        var loggedAccount = authenticationService.getLoggedAccount();
        var answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Odpoveď neexistuje."));

        if (!Objects.equals(answer.getQuestion().getAccount().getId(), loggedAccount.getId()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Prihlásený používateľ nie je autorom otázky tejto odpovede.");

        if (isSolved) {
            answer.getQuestion().setSolvedAnswer(answer);
        } else {
            answer.getQuestion().setSolvedAnswer(null);
        }
    }
}
