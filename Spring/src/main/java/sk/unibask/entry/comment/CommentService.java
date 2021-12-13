package sk.unibask.entry.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Answer;
import sk.unibask.data.model.Comment;
import sk.unibask.data.model.Question;
import sk.unibask.data.repository.CommentRepository;
import sk.unibask.data.repository.EntryRepository;

import java.util.Date;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntryRepository entryRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public CommentService(CommentRepository commentRepository, EntryRepository entryRepository, AuthenticationService authenticationService) {
        this.commentRepository = commentRepository;
        this.entryRepository = entryRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public Comment createNewComment(String text, Long entryId) {
        var comment = new Comment();
        comment.setEntryText(text);
        comment.setCreationDate(new Date());
        comment.setAccount(authenticationService.getLoggedAccount());
        comment.setEntry(entryRepository.findById(entryId).get());

        if (comment.getEntry() instanceof Question question) {
            question.setLastActivity(comment.getCreationDate());
        } else if (comment.getEntry() instanceof Answer answer) {
            answer.getQuestion().setLastActivity(comment.getCreationDate());
        }

        commentRepository.save(comment);
        return comment;
    }
}
