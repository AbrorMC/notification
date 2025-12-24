package uz.uzumtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzumtech.notification.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
