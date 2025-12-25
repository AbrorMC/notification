package uz.uzumtech.notification.dto.response;

public record SendingResponse(
        Data data
) {
    public record Data(
            Long notificationId
    ) {}
}
