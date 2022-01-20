package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
