package uz.uzumtech.notification.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzumtech.notification.entity.NotificationEntity;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @EntityGraph(value = "Notification.withMerchant", type = EntityGraph.EntityGraphType.FETCH)
    Optional<NotificationEntity> findWithMerchantById(Long id);
}
