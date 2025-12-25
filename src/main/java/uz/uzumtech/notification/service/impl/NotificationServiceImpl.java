package uz.uzumtech.notification.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.dto.request.SendingRequest;
import uz.uzumtech.notification.dto.response.SendingResponse;
import uz.uzumtech.notification.exception.UserNotFoundException;
import uz.uzumtech.notification.mapper.NotificationMapper;
import uz.uzumtech.notification.repository.NotificationRepository;
import uz.uzumtech.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;

    EntityManager entityManager;

    @Transactional
    @Override
    public SendingResponse send(SendingRequest request) {
        var entity = notificationMapper.toEntity(request, entityManager);
        var result = notificationRepository.save(entity);

        return notificationMapper.toDto(result);
    }
}
