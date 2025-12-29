package uz.uzumtech.notification.dto.response;

public record RegistrationResponseDto(
        Data data
) {
    public record Data(
            Long merchantId,
            String password
    ) {}
}
