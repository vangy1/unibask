package sk.unibask.entry.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Question;
import sk.unibask.data.repository.AccountRepository;
import sk.unibask.data.repository.CategoryRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.entry.EntityToDtoService;
import sk.unibask.notification.NotificationService;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionService questionService;

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AuthenticationService authenticationService;
    private final EntityToDtoService entityToDtoService;
    private final AccountRepository accountRepository;
    private final NotificationService notificationService;

    @Autowired
    public QuestionService(CategoryRepository categoryRepository, QuestionRepository questionRepository, AuthenticationService authenticationService, EntityToDtoService entityToDtoService, AccountRepository accountRepository, NotificationService notificationService) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.authenticationService = authenticationService;
        this.entityToDtoService = entityToDtoService;
        this.accountRepository = accountRepository;
        this.notificationService = notificationService;
    }


    @Transactional
    public Question createNewQuestion(String title, String text, String unformattedText, Long categoryId, boolean isAnonymous) {
        if (title == null || title.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Otázka musí mať názov.");
        if (text == null || text.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Otázka musí mať text.");
        if (categoryId == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Otázka musí mať kategóriu.");

        var loggedAccount = authenticationService.getLoggedAccount();

        var question = new Question();
        question.setTitle(title);
        question.setEntryText(text);
        question.setEntryTextUnformatted(unformattedText);
        question.setCreationDate(new Date());
        question.setLastActivity(question.getCreationDate());
        question.setCategory(categoryRepository.findById(categoryId).get());
        question.setAnonymous(isAnonymous);
        question.setAccount(loggedAccount);

        questionRepository.save(question);
        loggedAccount.getFollowingQuestions().add(question);

        notificationService.createNewCategoryNotification(question);
        return question;
    }


    @Transactional
    public QuestionDto getQuestion(long id) {
        var question = questionRepository.findOneWithAllData(id).get();
        Account loggedAccount = authenticationService.getLoggedAccount();
        question.getViewers().add(loggedAccount);
        QuestionDto questionDto = entityToDtoService.questionToQuestionDto(question, loggedAccount.getId());
        questionDto.setFollowed(question.getFollowerAccounts().contains(loggedAccount));
        return questionDto;
    }

    @Transactional
    public List<QuestionDto> getQuestions(String phrase, boolean followed, Long category, int page, int limit) {
        authenticationService.getLoggedAccount();
        if (phrase != null) phrase = phrase.toLowerCase();
        if (followed) {
            return questionService.getFollowedQuestions(phrase, page, limit);
        } else if (category != null) {
            return questionService.getQuestionsByCategory(phrase, category, page, limit);
        } else {
            return questionService.getAllQuestions(phrase, page, limit);
        }
    }


    @Transactional
    public List<QuestionDto> getAllQuestions(String phrase, int page, int limit) {
        return (phrase == null ? questionRepository.findAllQuestions() : questionRepository.findAllQuestions(phrase))
                .stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }

    @Transactional
    public List<QuestionDto> getQuestionsByCategory(String phrase, long categoryId, int page, int limit) {
        return (phrase == null ? questionRepository.findAllByCategory(categoryId) : questionRepository.findAllByCategory(categoryId, phrase))
                .stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }

    @Transactional
    public List<QuestionDto> getFollowedQuestions(String phrase, int page, int limit) {
        return (phrase == null ? questionRepository.findAllByFollowing(authenticationService.getLoggedAccount().getId()) :
                questionRepository.findAllByFollowing(authenticationService.getLoggedAccount().getId(), phrase))
                .stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }

    public void changeFollowStatus(String questionId, boolean followed) {
        if (followed) {
            questionService.followQuestion(questionId);
        } else {
            questionService.unfollowQuestion(questionId);
        }
    }

    @Transactional
    public void followQuestion(String questionId) {
        var account = authenticationService.getLoggedAccount();
        Question question = questionRepository.findById(Long.valueOf(questionId)).get();
        account.getFollowingQuestions().add(question);
        accountRepository.save(account);
    }

    @Transactional
    public void unfollowQuestion(String questionId) {
        var account = authenticationService.getLoggedAccount();
        Question question = questionRepository.findById(Long.valueOf(questionId)).get();
        account.getFollowingQuestions().remove(question);
        accountRepository.save(account);
    }
}
