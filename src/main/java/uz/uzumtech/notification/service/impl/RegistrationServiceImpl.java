package uz.uzumtech.notification.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.dto.request.RegistrationRequest;
import uz.uzumtech.notification.dto.response.RegistrationResponse;
import uz.uzumtech.notification.entity.MerchantEntity;
import uz.uzumtech.notification.exception.DuplicateUserException;
import uz.uzumtech.notification.mapper.MerchantMapper;
import uz.uzumtech.notification.repository.MerchantRepository;
import uz.uzumtech.notification.service.RegistrationService;
import uz.uzumtech.notification.utils.PasswordUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationServiceImpl implements RegistrationService {

    MerchantRepository merchantRepository;
    MerchantMapper merchantMapper;
    PasswordEncoder passwordEncoder;
    PasswordUtils passwordUtils;

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        String password = passwordUtils.generatePassword(10);
        String encodedPassword = passwordEncoder.encode(password);

        MerchantEntity merchant = merchantMapper.toEntity(request, encodedPassword);

        if (merchantRepository.existsByTaxNumber(merchant.getTaxNumber())) {
            throw new DuplicateUserException("User with tax number: " + merchant.getTaxNumber() + " already exists");
        }

        MerchantEntity savedMerchant = saveMerchant(merchant);

        return new RegistrationResponse(new RegistrationResponse.Data(savedMerchant.getId(), password));
    }

    @Transactional
    private MerchantEntity saveMerchant(MerchantEntity merchant) {
        return merchantRepository.save(merchant);
    }
}
