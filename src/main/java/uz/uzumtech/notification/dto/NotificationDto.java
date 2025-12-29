package uz.uzumtech.notification.dto;

public record NotificationDto(
        String key,
        String correlationId,
        String message
) {}
