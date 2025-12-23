package uz.uzumtech.notification.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.uzumtech.notification.exception.UserNotFoundException;
import uz.uzumtech.notification.repository.MerchantRepository;

@Service
@RequiredArgsConstructor
public class MerchantDetailsService implements UserDetailsService {
    private final MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return merchantRepository.findByLogin(username).orElseThrow(() -> new UserNotFoundException("User " + username + " not found"));
    }
}
