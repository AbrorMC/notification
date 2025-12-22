package uz.uzumtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzumtech.notification.entity.MerchantEntity;

public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {

}
