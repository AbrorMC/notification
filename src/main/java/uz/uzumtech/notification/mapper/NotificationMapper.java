package uz.uzumtech.notification.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import uz.uzumtech.notification.dto.NotificationDto;
import uz.uzumtech.notification.dto.request.SendingRequestDto;
import uz.uzumtech.notification.dto.response.SendingResponseDto;
import uz.uzumtech.notification.entity.NotificationEntity;
import uz.uzumtech.notification.utils.MapperUtils;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MapperUtils.class}
)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "merchant", source = "merchant", qualifiedByName = "getMerchant")
    @Mapping(target = "receiverInfo.value", source = "targetAddress")
    @Mapping(target = "text", source = "text")
    NotificationEntity toEntity(SendingRequestDto request, @Context EntityManager entityManager);

    @Mapping(target = "data.notificationId", source = "id")
    SendingResponseDto toResponse(NotificationEntity entity);

    @Mapping(target = "key", source = "entity.type")
    @Mapping(target = "correlationId", source = "correlationId")
    @Mapping(target = "message", source = "entity", qualifiedByName = "toJson")
    NotificationDto toDto(String correlationId, NotificationEntity entity);

}
