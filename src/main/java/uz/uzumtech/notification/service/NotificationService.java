package uz.uzumtech.notification.service;

import uz.uzumtech.notification.dto.request.SendingRequest;
import uz.uzumtech.notification.dto.response.SendingResponse;

public interface NotificationService {
    SendingResponse send(SendingRequest request);
}
