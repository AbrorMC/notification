package uz.uzumtech.notification.service;

import uz.uzumtech.notification.dto.request.SendingRequestDto;
import uz.uzumtech.notification.dto.response.SendingResponseDto;

public interface NotificationService {
    SendingResponseDto send(SendingRequestDto request);
}
