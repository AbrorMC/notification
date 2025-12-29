package uz.uzumtech.notification.dto.request;

import uz.uzumtech.notification.constant.enums.NotificationType;

public record SendingRequestDto(
        ReceiverData receiver,
        NotificationType type,
        Long merchant,
        String text
) {
    public String getTargetAddress() {
        if (receiver == null) return null;
        return switch (type) {
            case SMS -> receiver.phone();
            case EMAIL -> receiver.email();
            case PUSH -> receiver.firebaseToken();
        };
    }
}
