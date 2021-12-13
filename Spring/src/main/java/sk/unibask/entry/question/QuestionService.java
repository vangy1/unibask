package sk.unibask.entry.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Question;
import sk.unibask.data.repository.CategoryRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.entry.EntityToDtoService;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AuthenticationService authenticationService;
    private final EntityToDtoService entityToDtoService;

    @Autowired
    public QuestionService(CategoryRepository categoryRepository, QuestionRepository questionRepository, AuthenticationService authenticationService, EntityToDtoService entityToDtoService) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.authenticationService = authenticationService;
        this.entityToDtoService = entityToDtoService;
    }


    @Transactional
    public Question createNewQuestion(String title, String text, long categoryId, boolean isAnonymous) {
        var question = new Question();
        question.setTitle(title);
        question.setEntryText(text);
        question.setCreationDate(new Date());
        question.setLastActivity(question.getCreationDate());
        question.setCategory(categoryRepository.findById(categoryId).get());
        question.setAnonymous(isAnonymous);
        question.setAccount(authenticationService.getLoggedAccount());

        questionRepository.save(question);
        return question;
    }

    @Transactional
    public QuestionDto getQuestion(long id) {
        return entityToDtoService.questionToQuestionDto(questionRepository.findOneWithVotesViewers(id).get(), authenticationService.getLoggedAccount().getId());
    }

    @Transactional
    public QuestionDto getQuestionWithAnswers(long id) {
        var question = questionRepository.findOneWithAllData(id).get();
        System.out.println(question.getAnswers().size());
        Account loggedAccount = authenticationService.getLoggedAccount();
        question.getViewers().add(loggedAccount);
        return entityToDtoService.questionToQuestionDto(question, loggedAccount.getId());
    }

    @Transactional
    public List<QuestionDto> getQuestions(int page, int limit) {
        return questionRepository.findAllWithViewersVotesAnswersComments().stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }

    @Transactional
    public List<QuestionDto> getQuestionsByCategory(long categoryId, int page, int limit) {
        return questionRepository.findAllByCategoryWithViewersVotesAnswersComments(categoryId).stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }

    @Transactional
    public List<QuestionDto> getFollowedQuestions(int page, int limit) {
        return questionRepository.findAllByFollowingWithViewersVotesAnswersComments(authenticationService.getLoggedAccount().getId()).stream().skip((long) page * limit).limit(limit).map(question -> entityToDtoService.questionToQuestionDto(question, null)).toList();
    }
}
