package uz.uzumtech.notification.utils;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import uz.uzumtech.notification.entity.MerchantEntity;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapperUtils {

    ObjectMapper objectMapper;

    @Named("toJson")
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Named("getMerchant")
    public MerchantEntity getMerchant(Long merchantId, @Context EntityManager entityManager) {
        return entityManager.getReference(MerchantEntity.class, merchantId);
    }

}
