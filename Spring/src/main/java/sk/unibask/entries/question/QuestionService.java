package sk.unibask.entries.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Question;
import sk.unibask.data.model.Vote;
import sk.unibask.data.repository.AnswerRepository;
import sk.unibask.data.repository.CategoryRepository;
import sk.unibask.data.repository.QuestionRepository;
import sk.unibask.data.repository.VoteRepository;
import sk.unibask.entries.answer.AnswerDto;
import sk.unibask.entries.author.AuthorService;
import sk.unibask.entries.comment.CommentService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AuthenticationService authenticationService;
    private final VoteRepository voteRepository;
    private final AuthorService authorService;
    private final CommentService commentService;

    @Autowired
    public QuestionService(CategoryRepository categoryRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, AuthenticationService authenticationService, VoteRepository voteRepository, AuthorService authorService, CommentService commentService) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.authenticationService = authenticationService;
        this.voteRepository = voteRepository;
        this.authorService = authorService;
        this.commentService = commentService;
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
    public Question createNewQuestion(String title, String text, long categoryId, boolean isAnonymous) {
        var question = new Question();
        question.setTitle(title);
        question.setEntryText(text);
        question.setCreationDate(new Date());
        question.setCategory(categoryRepository.findById(categoryId).get());
        question.setAnonymous(isAnonymous);
        question.setAccount(authenticationService.getLoggedAccount());

        questionRepository.save(question);
        return question;
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
                        authorService.getAuthorDto(answer.getAccount()),
                        commentService.findCommentsByEntryId(answer.getId()),
                        answer == question.getSolvedAnswer()
                )).collect(Collectors.toList());

        return new QuestionDto(
                question.getId(),
                question.getEntryText(),
                voteRepository.findEntryReputation(question),
                question.getCreationDate(),
                authorService.getAuthorDto(question.getAccount()),
                question.getTitle(),
                question.getCategory().getTitle(),
                answers,
                commentService.findCommentsByEntryId(question.getId()),
                question.getSolvedAnswer() != null ? question.getSolvedAnswer().getId() : null);
    }


}
