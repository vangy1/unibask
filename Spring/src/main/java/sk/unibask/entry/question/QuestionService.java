package sk.unibask.entry.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.authentication.UserService;
import sk.unibask.data.model.Entry;
import sk.unibask.data.model.Question;
import sk.unibask.data.model.Vote;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.CategoryRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.data.repository.VoteRepository;
import sk.unibask.entry.answer.AnswerDto;
import sk.unibask.entry.comment.CommentService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionService questionService;

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AuthenticationService authenticationService;
    private final VoteRepository voteRepository;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public QuestionService(CategoryRepository categoryRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, AuthenticationService authenticationService, VoteRepository voteRepository, UserService userService, CommentService commentService) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.authenticationService = authenticationService;
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.commentService = commentService;
    }


    @Transactional
    public QuestionDto getQuestion(long id) {
        var question = questionRepository.findById(id).get();
        List<Vote> votes = question.getVotes();
        long downvotes = votes.stream().filter(vote -> vote.getValue() == -1).count();
        long upvotes = votes.stream().filter(vote -> vote.getValue() == 1).count();
        return new QuestionDto(question.getId(), question.getEntryText(), question.getViews(), voteRepository.findEntryReputation(question), question.getCreationDate(), null, question.getTitle(), question.getCategory().getTitle(), null, null, null, null);
    }

    @Transactional
    public Question createNewQuestion(String title, String text, long categoryId, boolean isAnonymous) {
        var question = new Question();
        question.setTitle(title);
        question.setEntryText(text);
        question.setCreationDate(new Date());
        question.setViews(0L);
        question.setCategory(categoryRepository.findById(categoryId).get());
        question.setAnonymous(isAnonymous);
        question.setAccount(authenticationService.getLoggedAccount());

        questionRepository.save(question);
        return question;
    }

    @Transactional
    public QuestionDto getQuestionWithAnswers(long id) {
        var question = questionRepository.findById(id).get();
        question.setViews(question.getViews() + 1);

        List<AnswerDto> answers = answerRepository.findAllByQuestionId(id).parallelStream()
                .map(answer -> new AnswerDto(
                        answer.getId(),
                        answer.getEntryText(),
                        voteRepository.findEntryReputation(answer),
                        answer.getCreationDate(),
                        userService.getUser(answer.getAccount()),
                        commentService.findCommentsByEntryId(answer.getId()),
                        answer == question.getSolvedAnswer()
                )).collect(Collectors.toList());

        return new QuestionDto(
                question.getId(),
                question.getEntryText(),
                question.getViews(),
                voteRepository.findEntryReputation(question),
                question.getCreationDate(),
                userService.getUser(question.getAccount()),
                question.getTitle(),
                question.getCategory().getTitle(),
                answers,
                commentService.findCommentsByEntryId(question.getId()),
                question.getSolvedAnswer() != null ? question.getSolvedAnswer().getId() : null,
                getLastActivityOfQuestion(question));
    }

    @Transactional
    public List<Entry> getAllEntriesOfQuestion(Question question) {
        List<Entry> entries = new ArrayList<>();
        entries.add(question);
        entries.addAll(question.getComments());
        entries.addAll(question.getAnswers());
        entries.addAll(question.getAnswers().parallelStream()
                .map(Entry.class::cast)
                .flatMap(entry -> entry.getComments()
                        .stream()
                        .map(Entry.class::cast)).toList());
        return entries;
    }

    private Date getLastActivityOfQuestion(Question question) {
        var mostRecentEntry = questionService.getAllEntriesOfQuestion(question).stream()
                .max(Comparator.comparing(Entry::getCreationDate))
                .orElse(null);
        return mostRecentEntry != null ? mostRecentEntry.getCreationDate() : null;
    }


    @Transactional
    public List<QuestionDto> getQuestions() {
        return sortQuestions(mapQuestionsToDto(questionRepository.findAll()));
    }

    @Transactional
    public List<QuestionDto> getQuestions(long categoryId) {
        return sortQuestions(mapQuestionsToDto(questionRepository.findByCategory(categoryRepository.findById(categoryId).get())));

    }

    private List<QuestionDto> sortQuestions(List<QuestionDto> questions) {
        return questions.stream().sorted(Comparator.comparing(QuestionDto::getLastActivity).reversed()).toList();
    }

    @Transactional
    public List<QuestionDto> mapQuestionsToDto(List<Question> questions) {
        return questions.stream().map(question -> new QuestionDto(
                question.getId(),
                null,
                question.getViews(),
                null,
                question.getCreationDate(),
                userService.getUser(question.getAccount()),
                question.getTitle(),
                question.getCategory().getTitle(),
                null,
                null,
                question.getSolvedAnswer() != null ? question.getSolvedAnswer().getId() : null,
                getLastActivityOfQuestion(question))).collect(Collectors.toList());
    }


}
