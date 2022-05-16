package sk.unibask.notification;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationDto> getNotifications() {
        return notificationService.getNotifications();
    }

    @PostMapping
    public void markNotificationsAsViewed(@RequestBody Map<String, Set<Long>> body) {
        notificationService.markNotificationsAsViewed(body.get("notificationIds"));
    }
}
