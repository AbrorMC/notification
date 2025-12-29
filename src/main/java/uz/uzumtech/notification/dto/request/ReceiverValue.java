package uz.uzumtech.notification.dto.request;

import jakarta.persistence.Embeddable;

@Embeddable
public record ReceiverValue(
        String value
) {}
