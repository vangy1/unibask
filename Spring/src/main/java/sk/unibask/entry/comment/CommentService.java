package sk.unibask.entry.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.*;
import sk.unibask.data.repository.CommentRepository;
import sk.unibask.data.repository.EntryRepository;
import sk.unibask.notification.NotificationService;

import java.util.Date;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final EntryRepository entryRepository;
    private final AuthenticationService authenticationService;
    private final NotificationService notificationService;

    @Autowired
    public CommentService(CommentRepository commentRepository, EntryRepository entryRepository, AuthenticationService authenticationService, NotificationService notificationService) {
        this.commentRepository = commentRepository;
        this.entryRepository = entryRepository;
        this.authenticationService = authenticationService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Comment createNewComment(String text, Long entryId) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        if (text == null || text.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Komentár musí mať text.");
        if (entryId == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Komentár musí byť priradený ku nejakému záznamu.");
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Komentovaný záznam neexistuje."));


        var comment = new Comment();
        comment.setEntryText(text);
        comment.setEntryTextUnformatted(text);
        comment.setCreationDate(new Date());
        comment.setAccount(loggedAccount);
        comment.setEntry(entry);

        if (comment.getEntry() instanceof Question question) {
            question.setLastActivity(comment.getCreationDate());
        } else if (comment.getEntry() instanceof Answer answer) {
            answer.getQuestion().setLastActivity(comment.getCreationDate());
        }

        commentRepository.save(comment);
        notificationService.createNewCommentNotification(comment);
        return comment;
    }
}
