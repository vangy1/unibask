package sk.unibask.feedback;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Feedback;
import sk.unibask.data.repository.FeedbackRepository;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AuthenticationService authenticationService;

    public FeedbackService(FeedbackRepository feedbackRepository, AuthenticationService authenticationService) {
        this.feedbackRepository = feedbackRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public void createFeedback(String feedbackText) {
        Account loggedAccount = authenticationService.getLoggedAccount();

        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setAccount(loggedAccount);
        feedbackRepository.save(feedback);
    }

}
