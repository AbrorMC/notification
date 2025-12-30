package uz.uzumtech.notification.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.component.kafka.producer.NotificationProducer;
import uz.uzumtech.notification.dto.request.SendingRequestDto;
import uz.uzumtech.notification.dto.response.SendingResponseDto;
import uz.uzumtech.notification.mapper.NotificationMapper;
import uz.uzumtech.notification.repository.NotificationRepository;
import uz.uzumtech.notification.service.NotificationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    NotificationProducer notificationProducer;

    EntityManager entityManager;

    @Transactional
    @Override
    public SendingResponseDto send(SendingRequestDto request) {
        var notificationEntity = notificationMapper.toEntity(request, entityManager);
        var result = notificationRepository.save(notificationEntity);

        var notificationDto = notificationMapper.toDto(UUID.randomUUID().toString(), notificationEntity);

        notificationProducer.sendMessage(notificationDto);

        return notificationMapper.toResponse(result);
    }
}
