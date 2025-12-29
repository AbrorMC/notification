package uz.uzumtech.notification.dto.response;

public record SendingResponseDto(
        Data data
) {
    public record Data(
            Long notificationId
    ) {}
}
