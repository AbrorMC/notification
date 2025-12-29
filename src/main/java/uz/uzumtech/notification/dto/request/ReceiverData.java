package uz.uzumtech.notification.dto.request;

public record ReceiverData(
        String phone,
        String email,
        String firebaseToken
) {}

