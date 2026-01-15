package uz.uzumtech.notification.dto;

public record WebhookDto(
    Long id,
    String status,
    String recipient,
    String type,
    String content
) {}
