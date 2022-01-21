package sk.unibask.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.*;
import sk.unibask.data.repository.AccountRepository;
import sk.unibask.data.repository.NotificationRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @Value("${web.url}")
    private String webUrl;

    public NotificationService(AuthenticationService authenticationService, AccountRepository accountRepository, NotificationRepository notificationRepository) {
        this.authenticationService = authenticationService;
        this.accountRepository = accountRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void createNewCategoryNotification(Question question) {
        Category category = question.getCategory();
        createNewNotification((question.isAnonymous() ? "Anonym" : question.getAccount().getUsername()) + " vytvoril novú otázku v kategórií " + category.getTitle(),
                question.getId(),
                accountRepository.findAccountsWithCategoryFollowed(category).stream()
                        .filter(account -> !Objects.equals(account.getId(), question.getAccount().getId()))
                        .collect(Collectors.toSet()));
    }

    @Transactional
    public void createNewAnswerNotification(Answer answer) {
        Question question = answer.getQuestion();
        createNewNotification((answer.isAnonymous() ? "Anonym" : answer.getAccount().getUsername()) + " odpovedal na otázku " + question.getTitle(),
                question.getId(),
                accountRepository.findAccountsWithQuestionFollowed(question).stream()
                        .filter(account -> !Objects.equals(account.getId(), answer.getAccount().getId()))
                        .collect(Collectors.toSet()));
    }

    @Transactional
    public void createNewCommentNotification(Comment comment) {
        Question question;
        if (comment.getEntry() instanceof Question q) {
            question = q;
        } else if (comment.getEntry() instanceof Answer answer) {
            question = answer.getQuestion();
        } else {
            return;
        }

        createNewNotification(comment.getAccount().getUsername() + " komentoval otázku " + question.getTitle(),
                question.getId(),
                accountRepository.findAccountsWithQuestionFollowed(question).stream()
                        .filter(account -> !Objects.equals(account.getId(), comment.getAccount().getId()))
                        .collect(Collectors.toSet()));
    }

    @Transactional
    public void createNewNotification(String title, Long questionId, Set<Account> accounts) {
        accounts.forEach(account -> {
            var notification = new Notification();
            notification.setTitle(title);
            notification.setUrl("question?id=" + questionId);
            notification.setAccount(account);
            notificationRepository.save(notification);
        });
    }


    @Transactional
    public List<NotificationDto> getNotifications() {
        return authenticationService.getLoggedAccount().getNotifications().stream()
                .map(notification -> new NotificationDto(notification.getId(), notification.getCreationDate(), notification.getTitle(), notification.getUrl(), notification.isViewed()))
                .limit(10).toList();
    }

    @Transactional
    public void markNotificationsAsViewed(Set<Long> notificationIds) {
        authenticationService.getLoggedAccount().getNotifications().stream()
                .filter(notification -> notificationIds.contains(notification.getId()))
                .forEach(notification -> notification.setViewed(true));
    }
}
