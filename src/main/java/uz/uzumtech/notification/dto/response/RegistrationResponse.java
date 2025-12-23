package uz.uzumtech.notification.dto.response;

public record RegistrationResponse(
        Data data
) {
    public record Data(
            Long merchantId,
            String password
    ) {}
}
