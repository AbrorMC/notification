package uz.uzumtech.notification.service;

import uz.uzumtech.notification.dto.request.RegistrationRequest;
import uz.uzumtech.notification.dto.response.RegistrationResponse;

public interface RegistrationService {
    RegistrationResponse register(RegistrationRequest request);
}
