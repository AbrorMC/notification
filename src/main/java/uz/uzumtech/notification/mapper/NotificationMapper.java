package uz.uzumtech.notification.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.uzumtech.notification.dto.request.SendingRequest;
import uz.uzumtech.notification.entity.NotificationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface NotificationMapper {

    NotificationEntity toEntity(SendingRequest request);
}
