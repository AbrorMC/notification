package uz.uzumtech.notification.service;

import uz.uzumtech.notification.dto.request.RegistrationRequestDto;
import uz.uzumtech.notification.dto.response.RegistrationResponseDto;

public interface RegistrationService {
    RegistrationResponseDto register(RegistrationRequestDto request);
}
