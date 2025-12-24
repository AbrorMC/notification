package uz.uzumtech.notification.dto.request;

import uz.uzumtech.notification.constant.enums.NotificationType;

public record SendingRequest(
        Receiver receiver,
        NotificationType type,
        Long merchant,
        String text
) {
    public record Receiver(
            String phone,
            String email,
            String firebaseToken
    ) {}
}
