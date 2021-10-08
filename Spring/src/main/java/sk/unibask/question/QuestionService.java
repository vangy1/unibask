package sk.unibask.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Question;
import sk.unibask.data.model.Vote;
import sk.unibask.data.repository.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private QuestionService questionService;


    public void getQuestions() {

    }

    @Transactional
    public QuestionDto getQuestion(long id) {
        var question = questionRepository.findById(id).get();
        List<Vote> votes = question.getVotes();
        long downvotes = votes.stream().filter(vote -> vote.getValue() == -1).count();
        long upvotes = votes.stream().filter(vote -> vote.getValue() == 1).count();
        return new QuestionDto(question.getId(), question.getEntryText(), voteRepository.findEntryReputation(question), question.getCreationDate(), null, question.getTitle(), question.getCategory().getTitle(), null, null, null);
    }

    @Transactional
    public void createNewQuestion(String title, String text, long categoryId, boolean isAnonymous) {
        var question = new Question();
        question.setTitle(title);
        question.setEntryText(text);
        question.setCreationDate(new Date());
        question.setCategory(categoryRepository.findById(categoryId).get());
        question.setAnonymous(isAnonymous);
        question.setAccount(authenticationService.getLoggedAccount());

        questionRepository.save(question);
    }

    @Transactional
    public QuestionDto getQuestionWithAnswers(long id) {
        var question = questionRepository.findById(id).get();

        List<AnswerDto> answers = answerRepository.findAllByQuestionId(id).parallelStream()
                .map(answer -> new AnswerDto(
                        answer.getId(),
                        answer.getEntryText(),
                        voteRepository.findEntryReputation(answer),
                        answer.getCreationDate(),
                        questionService.getAuthorDto(answer.getAccount()),
                        questionService.findCommentsByEntryId(answer.getId()),
                        answer == question.getSolvedAnswer()
                )).collect(Collectors.toList());

        return new QuestionDto(
                question.getId(),
                question.getEntryText(),
                voteRepository.findEntryReputation(question),
                question.getCreationDate(),
                questionService.getAuthorDto(question.getAccount()),
                question.getTitle(),
                question.getCategory().getTitle(),
                answers,
                questionService.findCommentsByEntryId(question.getId()),
                question.getSolvedAnswer().getId());
    }

    @Transactional
    public AuthorDto getAuthorDto(Account account) {
        return new AuthorDto(account.getId(), account.getUsername(), account.getAvatar(), null);
    }

    @Transactional
    public List<CommentDto> findCommentsByEntryId(Long entryId) {
        return commentRepository.findAllByEntryId(entryId).parallelStream().map(comment ->
                new CommentDto(comment.getId(), comment.getEntryText(), voteRepository.findEntryReputation(comment), comment.getCreationDate(), getAuthorDto(comment.getEntry().getAccount()))).collect(Collectors.toList());
    }
}
