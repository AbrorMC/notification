package uz.uzumtech.notification.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

public record RegistrationRequestDto(

        @NotBlank(message = "ИНН обязательно")
        @Pattern(regexp = "\\d{9}", message = "ИНН должен состоять из 9 цифр")
        String taxNumber,

        @NotBlank(message = "Имя компании обязательно")
        String companyName,

        @Email
        String email,

        @NotBlank(message = "Логин обязательно")
        String login,

        @NotBlank(message = "Вебхук обязательно")
        @URL(protocol = "https", message = "Должен быть валидный URL адрес")
        String webhook
) {}
