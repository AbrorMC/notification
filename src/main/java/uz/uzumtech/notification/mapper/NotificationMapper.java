package uz.uzumtech.notification.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import uz.uzumtech.notification.constant.enums.NotificationType;
import uz.uzumtech.notification.dto.request.SendingRequest;
import uz.uzumtech.notification.dto.response.SendingResponse;
import uz.uzumtech.notification.entity.MerchantEntity;
import uz.uzumtech.notification.entity.NotificationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "merchant", source = "merchant", qualifiedByName = "getMerchant")
    @Mapping(target = "receiverInfo", source = ".", qualifiedByName = "getReceiverInfo")
    @Mapping(target = "text", source = "text")
    NotificationEntity toEntity(SendingRequest request, @Context EntityManager entityManager);

    @Mapping(target = "data.notificationId", source = "merchant.id")
    SendingResponse toDto(NotificationEntity entity);

    @Named("getMerchant")
    default MerchantEntity getMerchant(Long merchantId, @Context EntityManager entityManager) {
        return entityManager.getReference(MerchantEntity.class, merchantId);
    }

    @Named("getReceiverInfo")
    default String getReceiverInfo(SendingRequest request) {
        return switch (request.type()) {
            case NotificationType.SMS -> request.receiver().phone();
            case NotificationType.EMAIL -> request.receiver().email();
            case NotificationType.PUSH -> request.receiver().firebaseToken();
        };
    }
}
