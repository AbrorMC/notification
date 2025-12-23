package uz.uzumtech.notification.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uz.uzumtech.notification.dto.request.RegistrationRequest;
import uz.uzumtech.notification.entity.MerchantEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MerchantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "name", source = "request.companyName")
    @Mapping(target = "password", source = "encodedPassword")
    MerchantEntity toEntity(RegistrationRequest request, String encodedPassword);
}
