package uz.uzumtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzumtech.notification.entity.MerchantEntity;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
    Optional<MerchantEntity> findByLogin(String username);

    boolean existsByTaxNumber(String taxNumber);
}
