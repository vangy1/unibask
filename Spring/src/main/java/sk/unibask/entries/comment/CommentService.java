package sk.unibask.entries.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Comment;
import sk.unibask.data.repository.CommentRepository;
import sk.unibask.data.repository.EntryRepository;
import sk.unibask.data.repository.VoteRepository;
import sk.unibask.entries.author.AuthorService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntryRepository entryRepository;
    private final VoteRepository voteRepository;
    private final AuthorService authorService;
    private final AuthenticationService authenticationService;

    @Autowired
    public CommentService(CommentRepository commentRepository, EntryRepository entryRepository, VoteRepository voteRepository, AuthorService authorService, AuthenticationService authenticationService) {
        this.commentRepository = commentRepository;
        this.entryRepository = entryRepository;
        this.voteRepository = voteRepository;
        this.authorService = authorService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public Comment createNewComment(String text, Long entryId) {
        var comment = new Comment();
        comment.setEntryText(text);
        comment.setCreationDate(new Date());
        comment.setAccount(authenticationService.getLoggedAccount());
        comment.setEntry(entryRepository.findById(entryId).get());

        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public List<CommentDto> findCommentsByEntryId(Long entryId) {
        return commentRepository.findAllByEntryId(entryId).parallelStream().map(comment ->
                new CommentDto(comment.getId(), comment.getEntryText(), voteRepository.findEntryReputation(comment), comment.getCreationDate(), authorService.getAuthorDto(comment.getEntry().getAccount()))).collect(Collectors.toList());
    }

    @Transactional
    public CommentDto commentToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getEntryText(), 0L, comment.getCreationDate(), authorService.getAuthorDto(comment.getAccount()));
    }
}
